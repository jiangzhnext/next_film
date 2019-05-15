package com.next.jiangzh.film.service.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPriceBO implements Serializable {

    private String cinemaId;
    private Double filmPrice;

}
