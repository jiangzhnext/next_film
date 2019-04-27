package com.next.jiangzh.film.controller.film.vo.request;

import com.next.jiangzh.film.controller.common.BaseVO;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import lombok.Data;

import java.io.Serializable;

@Data
public class DescribeFilmListReqVO extends BaseVO implements Serializable {

    private String showType="1";
    private String sortId="1";
    private String catId="99";
    private String sourceId="99";
    private String yearId="99";
    private String nowPage="1";
    private String pageSize="18";


    @Override
    public void checkParam() throws ParamErrorException {
        // TODO
    }

}
