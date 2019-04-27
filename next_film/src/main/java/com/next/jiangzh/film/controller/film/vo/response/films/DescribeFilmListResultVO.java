package com.next.jiangzh.film.controller.film.vo.response.films;

import lombok.Data;

import java.io.Serializable;

@Data
public class DescribeFilmListResultVO implements Serializable {

    private String filmId;
    private String filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;

}
