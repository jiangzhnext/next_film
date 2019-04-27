package com.next.jiangzh.film.controller.film.vo.response.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfoResultVO implements Serializable {

    private String catId;
    private String catName;
    private String isActive;

}
