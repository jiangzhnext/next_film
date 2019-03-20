package com.next.jiangzh.film.service.common.exception;

import lombok.Data;

@Data
public class CommonServiceExcetion extends Exception{

    private Integer code;
    private String errMsg;

    public CommonServiceExcetion(int code, String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

}
