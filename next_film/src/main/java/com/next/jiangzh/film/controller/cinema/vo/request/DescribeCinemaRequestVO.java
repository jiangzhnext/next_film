package com.next.jiangzh.film.controller.cinema.vo.request;

import com.next.jiangzh.film.controller.common.BaseVO;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import lombok.Data;

@Data
public class DescribeCinemaRequestVO extends BaseVO {

    private Integer brandId;
    private Integer hallType;
    private Integer districtId;
    private Integer pageSize;
    private Integer nowPage;


    @Override
    public void checkParam() throws ParamErrorException {

    }
}
