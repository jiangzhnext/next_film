package com.next.jiangzh.film.controller.film;

import com.next.jiangzh.film.controller.common.BaseResponseVO;
import com.next.jiangzh.film.controller.film.vo.request.DescribeFilmListReqVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    /*
        获取首页信息
     */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public BaseResponseVO describeIndex(){

        return null;
    }


    /*
        获取查询条件列表
     */
    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public BaseResponseVO getConditionList(
           @RequestParam(name = "catId",required = false,defaultValue = "99") String catId,
           @RequestParam(name = "sourceId",required = false,defaultValue = "99") String sourceId,
           @RequestParam(name = "yearId",required = false,defaultValue = "99") String yearId
    ){



        return null;
    }


    /*
        获取电影列表信息
     */
    @RequestMapping(value = "/getFilms",method = RequestMethod.GET)
    public BaseResponseVO getFilms(DescribeFilmListReqVO requestVO){

        return null;
    }


    /*
        获取电影列表信息
     */
    @RequestMapping(value = "/films/{searchStr}",method = RequestMethod.GET)
    public BaseResponseVO describeFilmDetails(@PathVariable(name = "searchStr") String searchStr,String searchType){



        return null;
    }




}
