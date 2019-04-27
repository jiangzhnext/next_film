package com.next.jiangzh.film.controller.film.vo.response.filmdetail;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDetailResultVO implements Serializable {

    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;

}
