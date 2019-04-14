package com.next.jiangzh.film.controller.cinema;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.jiangzh.film.config.properties.FilmProperties;
import com.next.jiangzh.film.controller.cinema.vo.CinemaDetailVO;
import com.next.jiangzh.film.controller.cinema.vo.CinemaFilmVO;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.service.cinema.CinemaServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {

    @Autowired
    private FilmProperties filmProperties;

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;


    @RequestMapping(value = "/getFields",method = RequestMethod.GET)
    public BaseResponseVO getFields(String cinemaId){
        // 获取元数据
        List<CinemaFilmVO> cinemaFilms = cinemaServiceAPI.describeFieldsAndFilmInfo(cinemaId);
        CinemaDetailVO cinemaDetailVO = cinemaServiceAPI.describeCinemaDetails(cinemaId);

        // 组织返回参数
        List<Map<String,CinemaFilmVO>> filmList = Lists.newArrayList();

        cinemaFilms.stream().forEach((film)->{
            Map<String,CinemaFilmVO> filmVOMap = Maps.newHashMap();
            filmVOMap.put("filmInfo",film);
            filmList.add(filmVOMap);
        });

        Map<String,Object> result = Maps.newHashMap();
        result.put("cinemaInfo",cinemaDetailVO);
        result.put("filmList",filmList);

        return BaseResponseVO.success(filmProperties.getImgPre(),result);
    }

}
