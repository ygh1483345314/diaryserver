package com.diary.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {


    @Bean
    private RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }



}
