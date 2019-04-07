package com.next.jiangzh.film.service.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.jiangzh.film.controller.cinema.vo.*;
import com.next.jiangzh.film.controller.cinema.vo.condition.AreaResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.BrandResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.HallTypeResVO;
import com.next.jiangzh.film.controller.cinema.vo.request.DescribeCinemaRequestVO;
import com.next.jiangzh.film.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaServiceAPI{

    @Autowired
    private FilmFieldTMapper filmFieldMapper;
    @Autowired
    private FilmCinemaTMapper cinemaMapper;
    @Autowired
    private FilmHallFilmInfoTMapper filmInfoMapper;
    @Autowired
    private FilmAreaDictTMapper areaDictMapper;
    @Autowired
    private FilmHallDictTMapper hallDictMapper;
    @Autowired
    private FilmBrandDictTMapper brandDictMapper;


    @Override
    public Page<CinemaVO> describeCinemaInfo(DescribeCinemaRequestVO describeCinemaRequestVO) {
        return null;
    }

    @Override
    public List<BrandResVO> describeBrandConditions(int brandId) {
        return null;
    }

    @Override
    public List<AreaResVO> describeAreaConditions(int areaId) {
        return null;
    }

    @Override
    public List<HallTypeResVO> describeHallTypeConditions(int hallTypeId) {
        return null;
    }

    @Override
    public CinemaDetailVO describeCinemaDetails(String cinemaId) {
        return null;
    }

    @Override
    public List<CinemaFilmVO> describeFieldsAndFilmInfo(String cinemaId) {
        return null;
    }

    @Override
    public CinemaFilmInfoVO describeFilmInfoByFieldId(String fieldId) {
        return null;
    }

    @Override
    public FieldHallInfoVO describeHallInfoByFieldId(String fieldId) {
        return null;
    }
}
