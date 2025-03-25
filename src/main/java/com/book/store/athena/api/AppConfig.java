package com.book.store.athena.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate () {

        return new RestTemplate();

    }

}
