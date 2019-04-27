package com.next.jiangzh.film.controller.film.vo.response.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceInfoResultVO implements Serializable {

    private String sourceId;
    private String sourceName;
    private String isActive;

}
