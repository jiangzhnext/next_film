package com.next.jiangzh.film.controller.film.vo.response.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class RankFilmListResultVO implements Serializable {

    private String filmId;
    private String imgAddress;
    private String filmName;

    private String boxNum;  // 票房排行使用

    private String expectNum; // 预期排行使用

    private String score; // 评分排行使用

}
