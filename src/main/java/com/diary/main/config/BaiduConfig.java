package com.diary.main.config;/*
Created by hao on 2019/11/26
*/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "baidu")
@Configuration
@Data
public class BaiduConfig {
    private List<String> serverList;
    private String url;
    private  String token;
    private  String server;

    public String getUrlToken(){
       return  this.url+"?site="+this.server+"&token="+this.token;
    }


}
