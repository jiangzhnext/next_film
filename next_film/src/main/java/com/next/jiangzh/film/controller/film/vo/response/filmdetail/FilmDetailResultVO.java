package com.next.jiangzh.film.controller.film.vo.response.filmdetail;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class FilmDetailResultVO implements Serializable {

    private String filmId;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;
    private Map<String,Object> info04 = Maps.newHashMap();
    private ImagesResultVO imgs;

}
