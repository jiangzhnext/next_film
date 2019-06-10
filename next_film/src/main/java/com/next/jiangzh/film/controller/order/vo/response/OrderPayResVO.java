package com.next.jiangzh.film.controller.order.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPayResVO implements Serializable {

    private String orderId;
    private Integer orderStatus;
    private String orderMsg="订单支付消息";

}
