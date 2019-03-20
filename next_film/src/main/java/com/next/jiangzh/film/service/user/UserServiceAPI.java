package com.next.jiangzh.film.service.user;

import com.next.jiangzh.film.controller.user.vo.EnrollUserVO;
import com.next.jiangzh.film.controller.user.vo.UserInfoVO;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;

public interface UserServiceAPI {

    /*
        用户登记接口
     */
    void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceExcetion;

    /*
        验证用户名是否存在
     */
    boolean checkUserName(String userName) throws CommonServiceExcetion;

    /*
        用户名密码验证
     */
    boolean userAuth(String userName,String userPwd) throws CommonServiceExcetion;

    /*
        获取用户信息
     */
    UserInfoVO describeUserInfo(String userId) throws CommonServiceExcetion;


    /*
        修改用户信息
     */
    UserInfoVO updateUserInfo(UserInfoVO userInfoVO) throws CommonServiceExcetion;

}
