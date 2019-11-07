package com.diary.main.config;/*
Created by hao on 2019/9/29
*/

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ElasticSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }




}
