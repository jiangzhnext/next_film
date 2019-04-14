package com.next.jiangzh.film.controller.cinema;

import com.next.jiangzh.film.config.properties.FilmProperties;
import com.next.jiangzh.film.service.cinema.CinemaServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {

    @Autowired
    private FilmProperties filmProperties;

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;




}
