package com.next.jiangzh.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.dao.entity.FilmOrderT;
import org.hibernate.validator.constraints.SafeHtml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmOrderTMapperTest {

    @Autowired
    private FilmOrderTMapper orderTMapper;

    @Test
    public void describeOrderDetailById(){
        String orderId = "415sdf58ew12ds5fe1";
        OrderDetailResVO orderDetailResVO = orderTMapper.describeOrderDetailsById(orderId);
        System.out.println("orderDetailResVO="+orderDetailResVO);
    }

    @Test
    public void describeOrderDetailByUser(){
        String userId = "1";
        Page<FilmOrderT> page = new Page<>(1,10);

        IPage<OrderDetailResVO> orderDetailResVOIPage = orderTMapper.describeOrderDetailsByUser(page, userId);

        orderDetailResVOIPage.getRecords().stream().forEach(
                System.out::println
        );

    }

}