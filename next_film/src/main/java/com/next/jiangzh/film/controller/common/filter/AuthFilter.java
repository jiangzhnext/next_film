package com.next.jiangzh.film.controller.common.filter;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.next.jiangzh.film.config.properties.JwtProperties;
import com.next.jiangzh.film.controller.auth.util.JwtTokenUtil;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.common.TraceUtil;
import com.next.jiangzh.film.dao.entity.NextUserT;
import com.next.jiangzh.film.dao.mapper.NextUserTMapper;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author jiangzh
 * @Date 2019/3/23 14:04
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private NextUserTMapper nextUserTMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getServletPath().equals("/auth")
                || request.getServletPath().equals("/user/check")
                || request.getServletPath().equals("/user/register")
                || request.getServletPath().equals("/swagger-ui.html")
                || request.getServletPath().startsWith("/swagger-resources")
                || request.getServletPath().startsWith("/v2/api-docs")
                || request.getServletPath().startsWith("/webjars/springfox-swagger-ui/")
        ) {
            chain.doFilter(request, response);
            return;
        }

        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            String userName = jwtTokenUtil.getUsernameFromToken(authToken);

            // 不正规的写法，注入的应该是Service层
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_name",userName);

            List<NextUserT> list = nextUserTMapper.selectList(queryWrapper);
            if(list!=null && list.size()>0){
                NextUserT nextUserT = list.get(0);
                TraceUtil.initThread(nextUserT.getUuid()+"");
            }

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    renderJson(response, BaseResponseVO.noLogin());
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                renderJson(response, BaseResponseVO.noLogin());
                return;
            }
        } else {
            //header没有带Bearer字段
            renderJson(response, BaseResponseVO.noLogin());
            return;
        }
        chain.doFilter(request, response);
    }


    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}