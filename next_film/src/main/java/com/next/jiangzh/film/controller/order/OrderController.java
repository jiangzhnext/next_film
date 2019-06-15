package com.next.jiangzh.film.controller.order;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.common.TraceUtil;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.controller.order.vo.response.OrderPayResVO;
import com.next.jiangzh.film.controller.order.vo.response.QRCodeResVO;
import com.next.jiangzh.film.example.alipay.trade.config.Configs;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import com.next.jiangzh.film.service.order.OrderServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    static {
        Configs.init("zfbinfo.properties");
    }

    @RequestMapping(value = "/buyTickets", method = RequestMethod.GET)
    public BaseResponseVO buyTickets(
            String fieldId, String soldSeats, String seatsName) throws CommonServiceExcetion {

        // 购票的限流措施
        RateLimiter rateLimiter = RateLimiter.create(20);
        rateLimiter.acquire(1);

        // soldSeats 验证是否为真实有效的座位信息
        try {
            orderServiceAPI.checkSeats(fieldId, soldSeats);
        } catch (IOException e) {
            throw new CommonServiceExcetion(404, "场次座位信息无法读取！");
        }
        // soldSeats 验证是否是未销售的座位
        orderServiceAPI.checkSoldSeats(fieldId, soldSeats);

        String userId = TraceUtil.getUserId();

        OrderDetailResVO orderDetailResVO = orderServiceAPI.saveOrder(soldSeats, seatsName, fieldId, userId);

        return BaseResponseVO.success(orderDetailResVO);
    }


    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.GET)
    public BaseResponseVO getOrderInfo(
            @RequestParam(name = "nowPage", required = false, defaultValue = "1") int nowPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) throws ParamErrorException, CommonServiceExcetion {

        checkGetOrderInfoParams(nowPage, pageSize);

        // 使用用户的JWT信息来获取内容
        String userId = TraceUtil.getUserId();

        IPage<OrderDetailResVO> orderDetailResVOIPage =
                orderServiceAPI.describeOrderInfoByUser(nowPage, pageSize, userId);

        return BaseResponseVO.success(
                orderDetailResVOIPage.getCurrent(),
                orderDetailResVOIPage.getPages(),
                orderDetailResVOIPage.getRecords());
    }

    private void checkGetOrderInfoParams
            (int nowPage, int pageSize) throws ParamErrorException {
        // nowpage必须大于1
        // pageSize必须大于0
    }


    @RequestMapping(value = "/getPayInfo", method = RequestMethod.GET)
    public BaseResponseVO getPayInfo(String orderId) throws CommonServiceExcetion {

        QRCodeResVO qrCodeResVO = orderServiceAPI.describeQRCodeAddress(orderId);

        return BaseResponseVO.success(qrCodeResVO);
    }

    @RequestMapping(value = "/getPayResult", method = RequestMethod.GET)
    public BaseResponseVO getPayResult(String orderId,
                                       @RequestParam(name = "tryNums", required = false, defaultValue = "1") Integer tryNums) throws CommonServiceExcetion {

        if(tryNums < 4){
            OrderPayResVO orderPayResVO = orderServiceAPI.describePayResult(orderId);
            return BaseResponseVO.success(orderPayResVO);
        }else{
            throw new CommonServiceExcetion(500,"支付超时");
        }
    }


    @RequestMapping(value = "/alipay/callback",method = RequestMethod.POST)
    public void alipayCallback(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {

        // 1、获取request里所有与alipay相关的参数，封装成一个map
        Map<String,String> paramterMap = Maps.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            System.out.println(parameterName+" , "+ request.getParameter(parameterName));
            paramterMap.put(parameterName,request.getParameter(parameterName));
        }
        // 2、验证请求是否是alipay返回的请求内容【验证请求合法性】
        // 很重要
        paramterMap.remove("sign_type");
        boolean isSuccess =
                AlipaySignature.rsaCheckV2(paramterMap, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());

        log.info("alipay callback result:{}",isSuccess);

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3、如果成功，返回一个success 25小时以内重发5~8次
        if(isSuccess){
            try {
                orderServiceAPI.orderPaySuccess(paramterMap.get("out_trade_no"));
            } catch (CommonServiceExcetion commonServiceExcetion) {
                log.error("支付失败，原因是：{}",commonServiceExcetion);
                out.print("unSuccess");
            }
            out.print("success");
        }else{
            out.print("unSuccess");
        }
    }

}
