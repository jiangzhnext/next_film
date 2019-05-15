package com.next.jiangzh.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.dao.entity.FilmOrderT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.next.jiangzh.film.service.order.bo.OrderPriceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author jiangzh
 * @since 2019-05-15
 */
public interface FilmOrderTMapper extends BaseMapper<FilmOrderT> {

    OrderDetailResVO describeOrderDetailsById(@Param("orderId") String orderId);

    IPage<OrderDetailResVO> describeOrderDetailsByUser(Page<FilmOrderT> page,@Param("userId") String userId);

    OrderPriceBO describeFilmPriceByFieldId(@Param("fieldId") String fieldId);

    String describeSoldSeats(@Param("fieldId") String fieldId);
}
