package com.next.jiangzh.film.controller.film.vo.response.filmdetail;

import lombok.Data;

import java.io.Serializable;

/*
    演员查询实体
 */
@Data
public class ActorResultVO implements Serializable {

    private String imgAddress;
    private String directorName;
    private String roleName;

}
