package com.next.jiangzh.film.controller.common;

import lombok.Data;
import org.springframework.aop.ThrowsAdvice;

public final class TraceUtil {

    private TraceUtil(){}

    private static  ThreadLocal<String> threadLocal
                = new ThreadLocal<>();

    public static void initThread(String userId){
        threadLocal.set(userId);
    }

    public static String getUserId(){
        return threadLocal.get();
    };


}
