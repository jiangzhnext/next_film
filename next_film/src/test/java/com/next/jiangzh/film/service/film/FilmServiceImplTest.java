package com.next.jiangzh.film.service.film;

import com.next.jiangzh.film.controller.film.vo.response.index.BannerInfoResultVO;
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
    public void describeHotFilms() {
    }

    @Test
    public void describeSoonFilms() {
    }

    @Test
    public void boxRandFilms() {
    }

    @Test
    public void expectRandFilms() {
    }

    @Test
    public void topRandFilms() {
    }

    @Test
    public void checkCondition() {
    }

    @Test
    public void describeCatInfos() {
    }

    @Test
    public void describeSourceInfos() {
    }

    @Test
    public void describeYearInfos() {
    }

    @Test
    public void describeFilms() {
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