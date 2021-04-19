package com.search.pkgs.common.conig.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 17:29
 */
@Configuration
public class RestTemplateConfig {

    @Bean(value = "restTemplate")
    public RestTemplate createRestTemplate() {

        int secondsOfOneMinute = 60 * 1000;

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(secondsOfOneMinute);
        requestFactory.setReadTimeout(secondsOfOneMinute);

        return new RestTemplate(requestFactory);

    }
}
