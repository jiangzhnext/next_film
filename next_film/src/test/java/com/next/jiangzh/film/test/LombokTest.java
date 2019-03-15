package com.next.jiangzh.film.test;

import com.next.jiangzh.film.example.service.bo.RegisterUserBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LombokTest {

    @Test
    public void lomboktest(){

//        RegisterUserBO registerUserBO = new RegisterUserBO();
//        registerUserBO.setUsername("username");
//        registerUserBO.setUserpwd("userpwd");
//        registerUserBO.setUuid("001");
//
//        System.out.println("bo="+registerUserBO);
    }

    @Test
    public void lombokbuildtest(){

        RegisterUserBO registerUserBO =
                RegisterUserBO.builder()
                .uuid("001")
                .username("admin")
                .userpwd("userpwd")
                .build();

        System.out.println("bo="+registerUserBO);
        log.info("registerUserBO="+registerUserBO);
    }

}
