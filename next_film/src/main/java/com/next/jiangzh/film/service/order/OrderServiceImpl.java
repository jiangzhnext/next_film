package com.next.jiangzh.film.service.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.next.jiangzh.film.common.utils.ToolUtils;
import com.next.jiangzh.film.config.properties.OrderProperties;
import com.next.jiangzh.film.controller.cinema.vo.FieldHallInfoVO;
import com.next.jiangzh.film.controller.order.vo.response.OrderDetailResVO;
import com.next.jiangzh.film.dao.mapper.FilmOrderTMapper;
import com.next.jiangzh.film.service.cinema.CinemaServiceAPI;
import com.next.jiangzh.film.service.common.exception.CommonServiceExcetion;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceAPI{

    @Autowired
    private FilmOrderTMapper filmOrderTMapper;

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private OrderProperties orderProperties;
    /*
        检查座位信息
        - 文件信息会在分布式的文件存储 | 对象存储里
        FILE_PATH + seat_address = C:/Users/A/Desktop/seats/cgs.json
     */
    @Override
    public void checkSeats(String fieldId, String seats) throws CommonServiceExcetion, IOException {
        FieldHallInfoVO fieldHallInfoVO = cinemaServiceAPI.describeHallInfoByFieldId(fieldId);
        if(fieldHallInfoVO==null || ToolUtils.isEmpty(fieldHallInfoVO.getSeatFile())){
            throw new CommonServiceExcetion(404,"场次编号不正确");
        }

        String seatsPath = orderProperties.getFilePathPre() + fieldHallInfoVO.getSeatFile();

        // 关闭文件流
        @Cleanup
        BufferedReader bufferedReader = new BufferedReader(
          new FileReader(seatsPath)
        );

        StringBuffer stringBuffer = new StringBuffer();
        String temp = new String();
        while ((temp = bufferedReader.readLine()) != null){
            stringBuffer.append(temp);
        }

        JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
        // 获取ids节点
        String idsStr = jsonObject.getString("ids");

        /*
            用户购买： 3,11,12
            ids: 1 - 24
         */
        List<String> idsList = Arrays.asList(idsStr.split(","));
        String[] seatArr = seats.split(",");

        for(String seatId : seatArr){
            boolean contains = idsList.contains(seatId);
            if(!contains){
                throw new CommonServiceExcetion(500,"传入的错误信息有误");
            }
        }

    }

    @Override
    public void checkSoldSeats(String fieldId, String seats) throws CommonServiceExcetion {

    }

    @Override
    public OrderDetailResVO saveOrder(String seatIds, String seatNames, String fieldId, String userId) throws CommonServiceExcetion {
        return null;
    }

    @Override
    public IPage<OrderDetailResVO> describeOrderInfoByUser(String userId) throws CommonServiceExcetion {
        return null;
    }

}
