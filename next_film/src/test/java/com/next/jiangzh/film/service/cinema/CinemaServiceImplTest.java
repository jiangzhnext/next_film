package com.next.jiangzh.film.service.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.jiangzh.film.controller.cinema.vo.CinemaVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.BrandResVO;
import com.next.jiangzh.film.controller.cinema.vo.request.DescribeCinemaRequestVO;
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
public class CinemaServiceImplTest {

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @Test
    public void describeCinemaInfo() {
        DescribeCinemaRequestVO describeCinemaRequestVO = new DescribeCinemaRequestVO();
        describeCinemaRequestVO.setBrandId(1);
        describeCinemaRequestVO.setDistrictId(1);
        describeCinemaRequestVO.setHallType(1);

        Page<CinemaVO> page = cinemaServiceAPI.describeCinemaInfo(describeCinemaRequestVO);

        log.info("nowPage:{},totalPage:{},recordNum:{},records:{}",page.getCurrent()
        ,page.getPages(),page.getTotal(),page.getRecords());

    }

    @Test
    public void describeBrandConditions() {

        List<BrandResVO> brands = cinemaServiceAPI.describeBrandConditions(1);
        brands.stream().forEach(
                data -> System.out.println(data)
        );

    }

    @Test
    public void describeAreaConditions() {
    }

    @Test
    public void describeHallTypeConditions() {
    }

    @Test
    public void describeCinemaDetails() {
        System.out.println(cinemaServiceAPI.describeCinemaDetails("1"));
    }

    @Test
    public void describeFieldsAndFilmInfo() {
    }

    @Test
    public void describeFilmInfoByFieldId() {
    }

    @Test
    public void describeHallInfoByFieldId() {
    }
}