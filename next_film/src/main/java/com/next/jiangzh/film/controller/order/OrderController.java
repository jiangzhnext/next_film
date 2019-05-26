package com.next.jiangzh.film.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.common.TraceUtil;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import com.next.jiangzh.film.service.order.OrderServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    @RequestMapping(value = "/buyTickets",method = RequestMethod.GET)
    public BaseResponseVO buyTickets(
            String fieldId,String soldSeats,String seatsName) throws CommonServiceExcetion {

        // soldSeats 验证是否为真实有效的座位信息
        try {
            orderServiceAPI.checkSeats(fieldId,soldSeats);
        } catch (IOException e) {
            throw new CommonServiceExcetion(404,"场次座位信息无法读取！");
        }
        // soldSeats 验证是否是未销售的座位
        orderServiceAPI.checkSoldSeats(fieldId,soldSeats);

        String userId = TraceUtil.getUserId();

        OrderDetailResVO orderDetailResVO = orderServiceAPI.saveOrder(soldSeats, seatsName, fieldId, userId);

        return BaseResponseVO.success(orderDetailResVO);
    }


    @RequestMapping(value = "/getOrderInfo",method = RequestMethod.GET)
    public BaseResponseVO getOrderInfo(
            @RequestParam(name = "nowPage",required = false,defaultValue = "1") int nowPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize) throws ParamErrorException, CommonServiceExcetion {

        checkGetOrderInfoParams(nowPage,pageSize);

        // 使用用户的JWT信息来获取内容
        String userId = TraceUtil.getUserId();

        IPage<OrderDetailResVO> orderDetailResVOIPage =
                orderServiceAPI.describeOrderInfoByUser(nowPage, pageSize, userId);

        return BaseResponseVO.success(
                orderDetailResVOIPage.getCurrent(),
                orderDetailResVOIPage.getPages(),
                orderDetailResVOIPage.getRecords());
    }

    private void checkGetOrderInfoParams
            (int nowPage,int pageSize) throws ParamErrorException {
        // nowpage必须大于1
        // pageSize必须大于0
    }

}
