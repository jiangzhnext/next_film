package com.next.jiangzh.film.controller.exception;

import lombok.Data;

@Data
public class NextFilmException extends Exception{

    private Integer code;
    private String errMsg;

    public NextFilmException(int code, String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

}
