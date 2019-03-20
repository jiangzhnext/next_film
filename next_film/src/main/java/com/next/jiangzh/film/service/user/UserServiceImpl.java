package com.next.jiangzh.film.service.user;

import com.next.jiangzh.film.common.utils.MD5Util;
import com.next.jiangzh.film.controller.user.vo.EnrollUserVO;
import com.next.jiangzh.film.controller.user.vo.UserInfoVO;
import com.next.jiangzh.film.dao.entity.NextUserT;
import com.next.jiangzh.film.dao.mapper.NextUserTMapper;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserServiceAPI{

    @Autowired
    private NextUserTMapper userTMapper;

    @Override
    public void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceExcetion {

        // EnrollUserVO -> NextUserT 转换
        NextUserT nextUserT = new NextUserT();
        // email  adress  phone复制
        BeanUtils.copyProperties(enrollUserVO,nextUserT);
        nextUserT.setUserName(enrollUserVO.getUsername());
        nextUserT.setUserPwd(MD5Util.encrypt(enrollUserVO.getPassword()));

        // 数据插入
        int isSuccess = userTMapper.insert(nextUserT);

        // 判断插入是否成功
        if(isSuccess!=1){
            throw new CommonServiceExcetion(501,"用户登记失败！");
        }

    }

    @Override
    public boolean checkUserName(String userName) throws CommonServiceExcetion {
        return false;
    }

    @Override
    public boolean userAuth(String userName, String userPwd) throws CommonServiceExcetion {
        return false;
    }

    @Override
    public UserInfoVO describeUserInfo(String userId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public UserInfoVO updateUserInfo(UserInfoVO userInfoVO) throws CommonServiceExcetion {
        return null;
    }
}
