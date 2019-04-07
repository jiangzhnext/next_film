package com.next.jiangzh.film.service.cinema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.jiangzh.film.controller.cinema.vo.*;
import com.next.jiangzh.film.controller.cinema.vo.condition.AreaResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.BrandResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.HallTypeResVO;
import com.next.jiangzh.film.controller.cinema.vo.request.DescribeCinemaRequestVO;
import com.next.jiangzh.film.dao.entity.FilmCinemaT;
import com.next.jiangzh.film.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaServiceAPI {

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
        // 组织Page对象
        Page<FilmCinemaT> page =
                new Page<>(
                        describeCinemaRequestVO.getNowPage(),
                        describeCinemaRequestVO.getPageSize());

        // 组织查询条件
        QueryWrapper<FilmCinemaT> filmCinemaTQueryWrapper =
                describeCinemaRequestVO.genWrapper();

        // 获取数据库返回
        IPage<FilmCinemaT> filmCinemaTIPage =
                cinemaMapper.selectPage(page, filmCinemaTQueryWrapper);

        // 组织返回值
        Page<CinemaVO> cinemaPage = new Page<>(describeCinemaRequestVO.getNowPage(),
                describeCinemaRequestVO.getPageSize());

        cinemaPage.setTotal(page.getTotal());

        // 将数据实体转换为表现层展示对象
        List<CinemaVO> cinemas =
                filmCinemaTIPage.getRecords().stream().map((data) -> {
            // 数据转换
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setUuid(data.getUuid()+"");
            cinemaVO.setCinemaName(data.getCinemaName());
            cinemaVO.setAddress(data.getCinemaAddress());
            cinemaVO.setMinimumPrice(data.getMinimumPrice()+"");
            return cinemaVO;

        }).collect(Collectors.toList());

        cinemaPage.setRecords(cinemas);

        return cinemaPage;
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
