package com.next.jiangzh.film.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = FilmProperties.FILM_PREFIX)
public class FilmProperties {

    public static final String FILM_PREFIX="film";

    private String imgPre;

}
