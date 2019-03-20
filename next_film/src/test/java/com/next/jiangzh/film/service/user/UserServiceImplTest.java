package com.next.jiangzh.film.service.user;

import com.next.jiangzh.film.controller.user.vo.EnrollUserVO;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Test
    public void userEnroll() throws CommonServiceExcetion {
        EnrollUserVO enrollUserVO = new EnrollUserVO();
        enrollUserVO.setUsername("1");
        enrollUserVO.setPassword("1");
        enrollUserVO.setAddress("1");
        enrollUserVO.setEmail("1");
        enrollUserVO.setPhone("1");

        userServiceAPI.userEnroll(enrollUserVO);

    }

    @Test
    public void checkUserName() {
    }

    @Test
    public void userAuth() {
    }

    @Test
    public void describeUserInfo() {
    }

    @Test
    public void updateUserInfo() {
    }
}