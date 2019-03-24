package com.next.jiangzh.film.controller.auth.controller.vo;

import com.next.jiangzh.film.controller.common.BaseVO;
import com.next.jiangzh.film.controller.exception.ParamErrorException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseVO extends BaseVO {

    private String randomKey;
    private String token;

    @Override
    public void checkParam() throws ParamErrorException {

    }
}
