package com.next.jiangzh.film.service.film;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.next.jiangzh.film.controller.film.vo.request.DescribeFilmListReqVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.CatInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.SourceInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.YearInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.ActorResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.FilmDetailResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.ImagesResultVO;
import com.next.jiangzh.film.controller.film.vo.response.films.DescribeFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.BannerInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.HotFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.RankFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.SoonFilmListResultVO;
import com.next.jiangzh.film.dao.entity.FilmBannerT;
import com.next.jiangzh.film.dao.entity.FilmInfoT;
import com.next.jiangzh.film.dao.mapper.*;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmServiceAPI{

    @Autowired
    private FilmSourceDictTMapper sourceDictTMapper;
    @Autowired
    private FilmYearDictTMapper yearDictTMapper;
    @Autowired
    private FilmCatDictTMapper catDictTMapper;

    @Autowired
    private FilmBannerTMapper bannerTMapper;

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private FilmDetailTMapper filmDetailTMapper;

    @Autowired
    private FilmActorTMapper actorTMapper;
    @Autowired
    private FilmActorRelaTMapper actorRelaTMapper;


    @Override
    public List<BannerInfoResultVO> describeBanners() throws CommonServiceExcetion {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_valid","0");

        List<FilmBannerT> banners = bannerTMapper.selectList(queryWrapper);

        List<BannerInfoResultVO> result = Lists.newArrayList();

        // 组织返回的结果
        banners.parallelStream().forEach((banner) -> {
            BannerInfoResultVO bannerInfoResultVO = new BannerInfoResultVO();
            bannerInfoResultVO.setBannerId(banner.getUuid()+"");
            bannerInfoResultVO.setBannerUrl(banner.getBannerUrl());
            bannerInfoResultVO.setBannerAddress(banner.getBannerAddress());

            result.add(bannerInfoResultVO);
        });

        return result;
    }

    @Override
    public List<HotFilmListResultVO> describeHotFilms() throws CommonServiceExcetion {
        // 默认热映的影片在首页中只查看8条记录
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<HotFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film) -> {
            HotFilmListResultVO result = new HotFilmListResultVO();

            result.setFilmId(film.getUuid()+"");
            result.setImgAddress(film.getImgAddress());
            result.setFilmType(film.getFilmType()+"");
            result.setFilmScore(film.getFilmScore());
            result.setFilmName(film.getFilmName());

            results.add(result);
        });

        return results;
    }

    @Override
    public List<SoonFilmListResultVO> describeSoonFilms() throws CommonServiceExcetion {
        // 默认即将上映的影片在首页中只查看8条记录
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<SoonFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film) -> {
            SoonFilmListResultVO vo = new SoonFilmListResultVO();

            vo.setFilmType(film.getFilmType()+"");
            vo.setImgAddress(film.getImgAddress());
            vo.setFilmName(film.getFilmName());
            vo.setFilmId(film.getUuid()+"");
            vo.setExpectNum(film.getFilmPresalenum()+"");

            vo.setShowTime(localTime2Str(film.getFilmTime()));

            results.add(vo);
        });

        return results;
    }

    private String localTime2Str(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDateTime);
    }


    @Override
    public List<RankFilmListResultVO> boxRandFilms() throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<RankFilmListResultVO> expectRandFilms() throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<RankFilmListResultVO> topRandFilms() throws CommonServiceExcetion {
        return null;
    }

    @Override
    public String checkCondition(String conditionId, String type) throws CommonServiceExcetion {

        return null;
    }

    @Override
    public List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<DescribeFilmListResultVO> describeFilms(DescribeFilmListReqVO filmListReqVO) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public FilmDetailResultVO describeFilmDetails(String searchStr, String searchType) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public String describeFilmBio(String filmId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public ImagesResultVO describeFilmImages(String filmId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public ActorResultVO describeDirector(String filmId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public List<ActorResultVO> describeActors(String filmId) throws CommonServiceExcetion {
        return null;
    }
}
