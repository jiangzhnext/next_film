package com.next.jiangzh.film.controller.cinema.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/*
    影院详情实体
 */
@Data
@Builder
public class CinemaDetailVO implements Serializable {

    private String cinemaId;
    private String imgUrl;
    private String cinemaName;
    private String cinemaAdress;
    private String cinemaPhone;


}
