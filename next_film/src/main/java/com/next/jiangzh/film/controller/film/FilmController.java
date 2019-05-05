package com.next.jiangzh.film.controller.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.next.jiangzh.film.config.properties.FilmProperties;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.film.vo.request.DescribeFilmListReqVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.CatInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.SourceInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.YearInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.ActorResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.FilmDetailResultVO;
import com.next.jiangzh.film.controller.film.vo.response.filmdetail.ImagesResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.BannerInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.HotFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.RankFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.SoonFilmListResultVO;
import com.next.jiangzh.film.dao.entity.FilmInfoT;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import com.next.jiangzh.film.service.film.FilmServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmServiceAPI filmServiceAPI;

    @Autowired
    private FilmProperties filmProperties;
    /*
        获取首页信息
     */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public BaseResponseVO describeIndex() throws CommonServiceExcetion {

        Map<String,Object> resultMap = Maps.newHashMap();
        // 获取banners
        List<BannerInfoResultVO> banners = filmServiceAPI.describeBanners();
        resultMap.put("banners",banners);

        // hotFilms
        List<HotFilmListResultVO> hotFilms = filmServiceAPI.describeHotFilms();
        int hotFilmNum = filmServiceAPI.describeIndexFilmNum("1");

        Map<String,Object> hotFilmMap = Maps.newHashMap();
        hotFilmMap.put("filmInfo",hotFilms);
        hotFilmMap.put("filmNum",hotFilmNum);

        resultMap.put("hotFilms",hotFilmMap);

        // soonFilms
        List<SoonFilmListResultVO> soonFilms = filmServiceAPI.describeSoonFilms();
        int soonFilmNum = filmServiceAPI.describeIndexFilmNum("2");

        Map<String,Object> soonFilmMap = Maps.newHashMap();
        soonFilmMap.put("filmInfo",soonFilms);
        soonFilmMap.put("filmNum",soonFilmNum);

        resultMap.put("soonFilms",soonFilmMap);

        // boxRanking
        List<RankFilmListResultVO> boxRandFilms = filmServiceAPI.boxRandFilms();

        resultMap.put("boxRanking",boxRandFilms);
        // expectRanking
        List<RankFilmListResultVO> expectRandFilms = filmServiceAPI.expectRandFilms();
        resultMap.put("expectRanking",expectRandFilms);
        // top100
        List<RankFilmListResultVO> topRandFilms = filmServiceAPI.topRandFilms();
        resultMap.put("top100",topRandFilms);

        return BaseResponseVO.success(filmProperties.getImgPre(),resultMap);
    }


    /*
        获取查询条件列表
     */
    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public BaseResponseVO getConditionList(
           @RequestParam(name = "catId",required = false,defaultValue = "99") String catId,
           @RequestParam(name = "sourceId",required = false,defaultValue = "99") String sourceId,
           @RequestParam(name = "yearId",required = false,defaultValue = "99") String yearId
    ) throws CommonServiceExcetion {

        // 检查入参
        catId = filmServiceAPI.checkCondition(catId,"cat");
        sourceId = filmServiceAPI.checkCondition(sourceId,"source");
        yearId = filmServiceAPI.checkCondition(yearId,"year");

        Map<String,Object> resultMap = Maps.newHashMap();

        List<CatInfoResultVO> catInfos = filmServiceAPI.describeCatInfos(catId);
        List<SourceInfoResultVO> sourceInfos = filmServiceAPI.describeSourceInfos(sourceId);
        List<YearInfoResultVO> yearInfos = filmServiceAPI.describeYearInfos(yearId);

        resultMap.put("catInfo",catInfos);
        resultMap.put("sourceInfo",sourceInfos);
        resultMap.put("yearInfo",yearInfos);

        return BaseResponseVO.success(resultMap);
    }


    /*
        获取电影列表信息
     */
    @RequestMapping(value = "/getFilms",method = RequestMethod.GET)
    public BaseResponseVO getFilms(DescribeFilmListReqVO requestVO) throws CommonServiceExcetion {

        IPage<FilmInfoT> page = filmServiceAPI.describeFilms(requestVO);

        return BaseResponseVO.success(page.getCurrent(),page.getPages(),filmProperties.getImgPre(),page.getRecords());
    }


    /*
        获取电影详情
     */
    @RequestMapping(value = "/films/{searchStr}",method = RequestMethod.GET)
    public BaseResponseVO describeFilmDetails(@PathVariable(name = "searchStr") String searchStr,String searchType) throws CommonServiceExcetion {

        // 根据查询条件，获取电影的编号
        FilmDetailResultVO filmDetailResultVO = filmServiceAPI.describeFilmDetails(searchStr, searchType);

        // biography
        String biography = filmServiceAPI.describeFilmBiography(filmDetailResultVO.getFilmId());

        // actors
        Map<String,Object> actors = Maps.newHashMap();

        ActorResultVO director = filmServiceAPI.describeDirector(filmDetailResultVO.getFilmId());

        List<ActorResultVO> actorResults = filmServiceAPI.describeActors(filmDetailResultVO.getFilmId());

        actors.put("director",director);
        actors.put("actors",actorResults);

        // imgs
        ImagesResultVO imagesResultVO = filmServiceAPI.describeFilmImages(filmDetailResultVO.getFilmId());

        filmDetailResultVO.getInfo04().put("biography",biography);
        filmDetailResultVO.getInfo04().put("actors",actors);

        filmDetailResultVO.setImgs(imagesResultVO);

        return BaseResponseVO.success(filmProperties.getImgPre(),filmDetailResultVO);
    }




}
