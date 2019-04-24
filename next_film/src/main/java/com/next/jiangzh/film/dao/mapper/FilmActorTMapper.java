package com.next.jiangzh.film.dao.mapper;

import com.next.jiangzh.film.controller.film.vo.ActorResultVO;
import com.next.jiangzh.film.dao.entity.FilmActorT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author jiangzh
 * @since 2019-04-22
 */
public interface FilmActorTMapper extends BaseMapper<FilmActorT> {

    /*
        根据电影编号，获取对应的演员列表
     */
    List<ActorResultVO> describeActorsByFilmId(@Param("filmId") String filmId);

}
