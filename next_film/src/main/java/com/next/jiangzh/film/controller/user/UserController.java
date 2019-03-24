package com.next.jiangzh.film.controller.user;

import com.next.jiangzh.film.common.utils.ToolUtils;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.exception.NextFilmException;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import com.next.jiangzh.film.service.user.UserServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/nextfilm/user/")
public class UserController {

    @Autowired
    private UserServiceAPI userServiceAPI;

    @RequestMapping(value = "check",method = RequestMethod.POST)
    public BaseResponseVO checkUser(String username) throws CommonServiceExcetion, NextFilmException {

        if(ToolUtils.isEmpty(username)){
            throw new NextFilmException(1,"username不能为空");
        }

        boolean hasUserName = userServiceAPI.checkUserName(username);
        if(hasUserName){
            return BaseResponseVO.serviceFailed("用户名已存在");
        }else{
            return BaseResponseVO.success();
        }

    }

}
