package com.next.jiangzh.film.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OrderServiceAPI {

    /*
        检查座位是否符合现状
     */
    void checkSeats(String fieldId,String seats) throws CommonServiceExcetion, IOException;

    /*
        检查座位是否为已售座位
     */
    void checkSoldSeats(String fieldId,String seats) throws CommonServiceExcetion;


    /*
        保存订单信息
     */
    OrderDetailResVO saveOrder(String seatIds,String seatNames,String fieldId,String userId) throws CommonServiceExcetion;


    /*
        根据用户编号，获取该用户购买过的电影票订单信息
     */
    IPage<OrderDetailResVO> describeOrderInfoByUser(String userId) throws CommonServiceExcetion;

}
