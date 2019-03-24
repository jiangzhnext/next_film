package com.next.jiangzh.film.controller.common.handler;

import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.exception.NextFilmException;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NextFilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO nextFilmException(NextFilmException e){
        return BaseResponseVO.serviceFailed(e.getErrMsg());
    }

    @ExceptionHandler(CommonServiceExcetion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO commonServiceException(CommonServiceExcetion e){
        return BaseResponseVO.serviceFailed(e.getCode(),e.getErrMsg());
    }

    @ExceptionHandler(ParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO paramErrorException(ParamErrorException e){
        return BaseResponseVO.serviceFailed(e.getCode(),e.getErrMsg());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponseVO exception(ExceptionHandler e){
        return BaseResponseVO.systemError();
    }
}
