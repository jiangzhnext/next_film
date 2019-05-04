package com.next.jiangzh.film.service.film;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.jiangzh.film.common.utils.ToolUtils;
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
import com.next.jiangzh.film.dao.entity.*;
import com.next.jiangzh.film.dao.mapper.*;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    /*
        票房排行 - 正在热映的电影，top10
     */
    @Override
    public List<RankFilmListResultVO> boxRandFilms() throws CommonServiceExcetion {

        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_box_office");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();

            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid()+"");
            result.setExpectNum(film.getFilmPresalenum()+"");
            result.setBoxNum(film.getFilmBoxOffice()+"");

            results.add(result);
        });

        return results;
    }

    /*
        期待观影人数排行 - 即将上映
     */
    @Override
    public List<RankFilmListResultVO> expectRandFilms() throws CommonServiceExcetion {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_preSaleNum");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();

            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid()+"");
            result.setExpectNum(film.getFilmPresalenum()+"");
            result.setBoxNum(film.getFilmBoxOffice()+"");

            results.add(result);
        });

        return results;
    }

    /*
        评分排行 - 正在热映的电影
     */
    @Override
    public List<RankFilmListResultVO> topRandFilms() throws CommonServiceExcetion {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_score");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();

            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid()+"");
            result.setExpectNum(film.getFilmPresalenum()+"");
            result.setBoxNum(film.getFilmBoxOffice()+"");

            results.add(result);
        });

        return results;
    }

    @Override
    public String checkCondition(String conditionId, String type) throws CommonServiceExcetion {

        switch (type){
            case "source":
                if("99".equals(conditionId)){
                    return conditionId;
                }
                FilmSourceDictT filmSourceDictT = sourceDictTMapper.selectById(conditionId);
                if(filmSourceDictT!=null && ToolUtils.isNotEmpty(filmSourceDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            case "year":
                if("99".equals(conditionId)){
                    return conditionId;
                }
                FilmYearDictT filmYearDictT = yearDictTMapper.selectById(conditionId);
                if(filmYearDictT!=null && ToolUtils.isNotEmpty(filmYearDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            case "cat":
                if("99".equals(conditionId)){
                    return conditionId;
                }
                FilmCatDictT filmCatDictT = catDictTMapper.selectById(conditionId);
                if(filmCatDictT!=null && ToolUtils.isNotEmpty(filmCatDictT.getUuid()+"")){
                    return conditionId;
                }else{
                    return "99";
                }
            default:
                throw new CommonServiceExcetion(404,"invalid conditonType!!");
        }
    }

    @Override
    public List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceExcetion {
        List<FilmCatDictT> catDicts
                = catDictTMapper.selectList(null);

        List<CatInfoResultVO> results =
                catDicts.stream().map((data)->{
                    CatInfoResultVO result = new CatInfoResultVO();
                    result.setCatId(data.getUuid()+"");
                    result.setCatName(data.getShowName());

                    if (catId.equals(data.getUuid()+"")) {
                        result.setIsActive("true");
                    }else{
                        result.setIsActive("false");
                    }
                    return result;
                }).collect(Collectors.toList());

        return results;
    }

    @Override
    public List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceExcetion {
        List<FilmSourceDictT> sourceDicts
                = sourceDictTMapper.selectList(null);

        List<SourceInfoResultVO> results =
                sourceDicts.stream().map((data)->{
                    SourceInfoResultVO result = new SourceInfoResultVO();
                    result.setSourceId(data.getUuid()+"");
                    result.setSourceName(data.getShowName());

                    if(sourceId.equals(data.getUuid()+"")){
                        result.setIsActive("true");
                    }else{
                        result.setIsActive("false");
                    }
                   return result;
                }).collect(Collectors.toList());

        return results;
    }

    @Override
    public List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceExcetion {
        List<FilmYearDictT> filmYearDicts
                = yearDictTMapper.selectList(null);

        List<YearInfoResultVO> results =
                filmYearDicts.stream().map((data)->{
                    YearInfoResultVO result = new YearInfoResultVO();
                    result.setYearId(data.getUuid()+"");
                    result.setYearName(data.getShowName());
                    if(yearId.equals(data.getUuid()+"")){
                        result.setIsActive("true");
                    }else{
                        result.setIsActive("false");
                    }
                    return result;
                }).collect(Collectors.toList());

        return results;
    }

    @Override
    public IPage<FilmInfoT> describeFilms(DescribeFilmListReqVO filmListReqVO) throws CommonServiceExcetion {

        Page<FilmInfoT> infoPage =
                new Page<>(Long.parseLong(filmListReqVO.getNowPage()),Long.parseLong(filmListReqVO.getPageSize()));

        // 排序方式 1-按热门搜索，2-按时间搜索，3-按评价搜索
        Map<String,String> sortMap = Maps.newHashMap();
        sortMap.put("1","film_preSaleNum");
        sortMap.put("2","film_time");
        sortMap.put("3","film_score");
        // hashMap搜索的时间复杂度是 log0

        infoPage.setDesc(sortMap.get(filmListReqVO.getSortId()));

        QueryWrapper queryWrapper = new QueryWrapper();
        // 判断待搜索列表内容  1-正在热映，2-即将上映，3-经典影片
        queryWrapper.eq("film_status",filmListReqVO.getShowType());

        // 组织QueryWrapper的内容
        if(!"99".equals(filmListReqVO.getSourceId())){
            queryWrapper.eq("film_source",filmListReqVO.getSourceId());
        }
        if(!"99".equals(filmListReqVO.getYearId())){
            queryWrapper.eq("film_date",filmListReqVO.getYearId());
        }
        // #3#2#12   #1# 11 111
        if(!"99".equals(filmListReqVO.getCatId())){
            queryWrapper.like("film_cats","#"+filmListReqVO.getCatId()+"#");
        }

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(infoPage, queryWrapper);

        return iPage;
    }

    @Override
    public FilmDetailResultVO describeFilmDetails(String searchStr, String searchType) throws CommonServiceExcetion {
        FilmDetailResultVO result;
        // 0表示按照编号查找，1表示按照名称查找
        if("0".equals(searchType)){
            result = filmInfoTMapper.describeFilmDetailByFilmId(searchStr);
        }else{
            result = filmInfoTMapper.describeFilmDetailByFilmName(searchStr);
        }

        return result;
    }

    @Override
    public String describeFilmBiography(String filmId) throws CommonServiceExcetion {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        String biography="";

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            FilmDetailT detailT = list.get(0);
            biography = detailT.getBiography();
        }

        return biography;
    }

    @Override
    public ImagesResultVO describeFilmImages(String filmId) throws CommonServiceExcetion {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        ImagesResultVO imagesResultVO = new ImagesResultVO();

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            FilmDetailT detailT = list.get(0);

            String[] images = detailT.getFilmImgs().split(",");

            // 验证images是否存在同时是不是五个

            imagesResultVO.setMainImg(images[0]);
            imagesResultVO.setImg01(images[1]);
            imagesResultVO.setImg02(images[2]);
            imagesResultVO.setImg03(images[3]);
            imagesResultVO.setImg04(images[4]);
        }

        return imagesResultVO;
    }

    @Override
    public ActorResultVO describeDirector(String filmId) throws CommonServiceExcetion {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        String directorId="";
        ActorResultVO resultVO = new ActorResultVO();

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            FilmDetailT detailT = list.get(0);
            directorId =  detailT.getDirectorId()+"";
        }

        // 根据filmId获取导演的编号
        if(ToolUtils.isNotEmpty(directorId)){
            FilmActorT filmActorT = actorTMapper.selectById(directorId);
            resultVO.setDirectorName(filmActorT.getActorName());
            resultVO.setImgAddress(filmActorT.getActorImg());
        }
        return resultVO;
    }

    @Override
    public List<ActorResultVO> describeActors(String filmId) throws CommonServiceExcetion {

        List<ActorResultVO> results = actorTMapper.describeActorsByFilmId(filmId);

        return results;
    }
}
