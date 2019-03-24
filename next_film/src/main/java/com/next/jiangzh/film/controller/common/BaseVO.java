package com.next.jiangzh.film.controller.common;

import com.next.jiangzh.film.controller.exception.ParamErrorException;

public abstract class BaseVO {


    public abstract void checkParam() throws ParamErrorException;

}
