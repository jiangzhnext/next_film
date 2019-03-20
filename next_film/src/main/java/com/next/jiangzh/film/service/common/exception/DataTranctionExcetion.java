package com.next.jiangzh.film.service.common.exception;

import lombok.Data;

@Data
public class DataTranctionExcetion extends Exception{

    private Integer code;
    private String errMsg;

    public DataTranctionExcetion(int code,String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

}
