package com.next.jiangzh.film.controller.order.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class QRCodeResVO implements Serializable {

    private String orderId;
    private String QRCodeAddress;

}
