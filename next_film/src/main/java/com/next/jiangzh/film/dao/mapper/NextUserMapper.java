package com.next.jiangzh.film.dao.mapper;

import com.next.jiangzh.film.dao.entity.NextUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiangzh
 * @since 2019-03-12
 */
public interface NextUserMapper extends BaseMapper<NextUser> {

    List<NextUser> getUsers();

}
