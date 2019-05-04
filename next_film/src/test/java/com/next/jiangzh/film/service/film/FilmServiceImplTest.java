package com.next.jiangzh.film.service.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.jiangzh.film.controller.film.vo.request.DescribeFilmListReqVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.CatInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.SourceInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.condition.YearInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.BannerInfoResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.HotFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.RankFilmListResultVO;
import com.next.jiangzh.film.controller.film.vo.response.index.SoonFilmListResultVO;
import com.next.jiangzh.film.dao.entity.FilmInfoT;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceImplTest {

    @Autowired
    private FilmServiceAPI filmServiceAPI;

    @Test
    public void describeBanners() {

        try {
            List<BannerInfoResultVO> result = filmServiceAPI.describeBanners();
            result.stream().forEach(
                    System.out::println
            );

        } catch (CommonServiceExcetion commonServiceExcetion) {
            commonServiceExcetion.printStackTrace();
        }

    }

    @Test
    public void describeHotFilms() throws CommonServiceExcetion {
        List<HotFilmListResultVO> hotFilmListResultVOS = filmServiceAPI.describeHotFilms();
        hotFilmListResultVOS.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void describeSoonFilms() throws CommonServiceExcetion {
        List<SoonFilmListResultVO> results = filmServiceAPI.describeSoonFilms();
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void boxRandFilms() throws CommonServiceExcetion {
        List<RankFilmListResultVO> results = filmServiceAPI.boxRandFilms();
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void expectRandFilms() throws CommonServiceExcetion {
        List<RankFilmListResultVO> results = filmServiceAPI.expectRandFilms();
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void topRandFilms() throws CommonServiceExcetion {
        List<RankFilmListResultVO> results = filmServiceAPI.topRandFilms();
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void checkCondition() throws CommonServiceExcetion {
        String conditionId="5";
        String type="year";
        String checkCondition = filmServiceAPI.checkCondition(conditionId, type);
        System.out.println("checkCondition="+checkCondition);
    }

    @Test
    public void describeCatInfos() throws CommonServiceExcetion {
        List<CatInfoResultVO> results = filmServiceAPI.describeCatInfos("3");
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void describeSourceInfos() throws CommonServiceExcetion {
        List<SourceInfoResultVO> results = filmServiceAPI.describeSourceInfos("1");
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void describeYearInfos() throws CommonServiceExcetion {
        List<YearInfoResultVO> results = filmServiceAPI.describeYearInfos("5");
        results.stream().forEach(
                System.out::println
        );
    }

    @Test
    public void describeFilms() throws CommonServiceExcetion {
        DescribeFilmListReqVO reqVO = new DescribeFilmListReqVO();

        IPage<FilmInfoT> filmInfoTIPage = filmServiceAPI.describeFilms(reqVO);

        System.out.println(filmInfoTIPage.getCurrent()+" , "+filmInfoTIPage.getPages()+" , "+filmInfoTIPage.getRecords().size());
    }

    @Test
    public void describeFilmDetails() {
    }

    @Test
    public void describeFilmBio() {
    }

    @Test
    public void describeFilmImages() {
    }

    @Test
    public void describeDirector() {
    }

    @Test
    public void describeActors() {
    }
}