package com.next.jiangzh.film.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
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
        String seats = "1,3,4";
        orderServiceAPI.checkSeats(fieldId,seats);
    }

    @Test
    public void checkSoldSeats() throws CommonServiceExcetion {
        String fieldId = "1";
        String seats = "1,3,0";
        orderServiceAPI.checkSoldSeats(fieldId,seats);
    }


    @Test
    public void describeOrderInfoByUser() throws CommonServiceExcetion {
        String userId = "1";
        int nowPage = 1;
        int pageSize = 10;

        IPage<OrderDetailResVO> orderDetailResVOIPage = orderServiceAPI.describeOrderInfoByUser(nowPage, pageSize, userId);
        orderDetailResVOIPage.getRecords().stream().forEach(
                System.out::println
        );

    }

    @Test
    public void saveOrder() throws CommonServiceExcetion {
        String field = "1";
        String seatIds = "10,11,12";
        String seatNames = "健康的交流开发";
        String userId = "1";

        System.out.println(orderServiceAPI.saveOrder(seatIds,seatNames,field,userId));

    }

}