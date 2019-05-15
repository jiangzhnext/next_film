package com.next.jiangzh.film.controller.order.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailResVO implements Serializable {

    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderStatus;

}
