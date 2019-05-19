package com.next.jiangzh.film.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = OrderProperties.ORDER_PREFIX)
public class OrderProperties {

    public static final String ORDER_PREFIX="order";

    private String filePathPre;

}
