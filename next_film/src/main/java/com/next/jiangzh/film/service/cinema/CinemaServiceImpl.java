package com.next.jiangzh.film.service.cinema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.next.jiangzh.film.controller.cinema.vo.*;
import com.next.jiangzh.film.controller.cinema.vo.condition.AreaResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.BrandResVO;
import com.next.jiangzh.film.controller.cinema.vo.condition.HallTypeResVO;
import com.next.jiangzh.film.controller.cinema.vo.request.DescribeCinemaRequestVO;
import com.next.jiangzh.film.dao.entity.FilmAreaDictT;
import com.next.jiangzh.film.dao.entity.FilmBrandDictT;
import com.next.jiangzh.film.dao.entity.FilmCinemaT;
import com.next.jiangzh.film.dao.entity.FilmHallDictT;
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
    @Autowired
    private FilmOrderTMapper filmOrderTMapper;

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

    public boolean checkCondition(int conditionId,String conditionType){
        switch (conditionType){
            case "brand":
                FilmBrandDictT filmBrandDictT = brandDictMapper.selectById(conditionId);
                if(filmBrandDictT!=null && filmBrandDictT.getUuid()!=null){
                    return true;
                }else{
                    return false;
                }
            case "area":
                FilmAreaDictT filmAreaDictT = areaDictMapper.selectById(conditionId);
                if (filmAreaDictT!=null && filmAreaDictT.getUuid()!=null){
                    return true;
                }else{
                    return false;
                }
            case "hallType":
                FilmHallDictT filmHallDictT = hallDictMapper.selectById(conditionId);
                if(filmHallDictT!=null && filmHallDictT.getUuid()!=null){
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    public List<BrandResVO> describeBrandConditions(final int brandId) {
        // 获取所有列表
        List<FilmBrandDictT> brands = brandDictMapper.selectList(null);

        // 并且将对应的品牌设置为isActive=true
        List<BrandResVO> result
                = brands.stream().map((data)->{

            BrandResVO brandResVO = new BrandResVO();
            if(brandId == data.getUuid()){
                brandResVO.setIsActive("true");
            }else{
                brandResVO.setIsActive("false");
            }
            brandResVO.setBrandId(data.getUuid()+"");
            brandResVO.setBrandName(data.getShowName());
            return  brandResVO;

        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<AreaResVO> describeAreaConditions(final int areaId) {
        // 获取所有列表
        List<FilmAreaDictT> areaDicts = areaDictMapper.selectList(null);

        // 并且将对应的区域设置为isActive=true
        List<AreaResVO> result
                = areaDicts.stream().map((data)->{

            AreaResVO areaResVO = new AreaResVO();
            if(areaId == data.getUuid()){
                areaResVO.setIsActive("true");
            }else{
                areaResVO.setIsActive("false");
            }
            areaResVO.setAreaId(data.getUuid()+"");
            areaResVO.setAreaName(data.getShowName());
            return  areaResVO;

        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<HallTypeResVO> describeHallTypeConditions(int hallTypeId) {
        // 获取所有列表
        List<FilmHallDictT> areaDicts = hallDictMapper.selectList(null);

        // 并且将对应的区域设置为isActive=true
        List<HallTypeResVO> result
                = areaDicts.stream().map((data)->{

            HallTypeResVO hallTypeResVO = new HallTypeResVO();
            if(hallTypeId == data.getUuid()){
                hallTypeResVO.setIsActive("true");
            }else{
                hallTypeResVO.setIsActive("false");
            }
            hallTypeResVO.setHalltypeId(data.getUuid()+"");
            hallTypeResVO.setHalltypeName(data.getShowName());
            return  hallTypeResVO;

        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public CinemaDetailVO describeCinemaDetails(String cinemaId) {
        FilmCinemaT data = cinemaMapper.selectById(cinemaId);

        CinemaDetailVO cinemaDetailVO =
                CinemaDetailVO.builder()
                .cinemaAdress(data.getCinemaAddress())
                .cinemaId(data.getUuid()+"")
                .cinemaName(data.getCinemaName())
                .cinemaPhone(data.getCinemaPhone())
                .imgUrl(data.getImgAddress()).build();

        return cinemaDetailVO;
    }

    @Override
    public List<CinemaFilmVO> describeFieldsAndFilmInfo(String cinemaId) {
        // 确认CinemaId是否有效
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uuid",cinemaId);
        Integer isNull = cinemaMapper.selectCount(queryWrapper);
        if(isNull == 0){
            return Lists.newArrayList();
        }
        return filmFieldMapper.describeFieldList(cinemaId);
    }

    @Override
    public CinemaFilmInfoVO describeFilmInfoByFieldId(String fieldId) {
        return filmFieldMapper.describeFilmInfoByFieldId(fieldId);
    }

    @Override
    public FieldHallInfoVO describeHallInfoByFieldId(String fieldId) {

        FieldHallInfoVO fieldHallInfoVO = filmFieldMapper.describeHallInfo(fieldId);
        // 调用订单，获取已售座位信息
        String soldSeats = filmOrderTMapper.describeSoldSeats(fieldId);
        fieldHallInfoVO.setSoldSeats(soldSeats);

        return fieldHallInfoVO;
    }

}
