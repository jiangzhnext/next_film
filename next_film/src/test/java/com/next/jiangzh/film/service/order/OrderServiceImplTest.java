package com.next.jiangzh.film.service.order;

import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    @Test
    public void checkSeats() throws CommonServiceExcetion, IOException {
        String fieldId = "1";
        String seats = "1,3,0";
        orderServiceAPI.checkSeats(fieldId,seats);
    }

    @Test
    public void checkSoldSeats() {
    }

    @Test
    public void saveOrder() {
    }

    @Test
    public void describeOrderInfoByUser() {
    }
}