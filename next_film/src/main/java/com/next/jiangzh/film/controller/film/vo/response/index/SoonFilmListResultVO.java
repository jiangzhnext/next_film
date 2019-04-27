package com.next.jiangzh.film.controller.film.vo.response.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class SoonFilmListResultVO implements Serializable {

    private String filmId;
    private String filmType;
    private String imgAddress;
    private String filmName;
    private String expectNum;
    private String showTime;


}
