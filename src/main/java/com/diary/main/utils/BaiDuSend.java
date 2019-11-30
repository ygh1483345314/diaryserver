package com.diary.main.utils;/*
Created by hao on 2019/11/26
*/
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.config.BaiduConfig;
import com.diary.main.model.Article;
import com.diary.main.service.ArticleService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@Component
@Configuration
@EnableScheduling
public  class  BaiDuSend  {
    @Autowired
    private BaiduConfig baiduConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArticleService articleService;

//    private  static  RestTemplate restTemplate=new RestTemplate();
    private  static HttpHeaders headers;
    static{
        headers = new HttpHeaders();
        headers.add("Host", "data.zz.baidu.com");
        headers.add("User-Agent", "curl/7.12.1");
        headers.add("Content-Length", "83");
        headers.add("Content-Type", "text/plain");
    }
//    12:30 发送一次百度推送
    @Scheduled(cron = "0 30 0 * * ?")
    public String sendPost(){
        String api_url = baiduConfig.getUrlToken();
        StringBuilder urlBuffer = new StringBuilder();
        List<Integer>  idList=articleService.findListByStatusAndEntry();
        String url=baiduConfig.getServer();
        urlBuffer.append(url+"\n");
        for (Integer id : idList) {
            urlBuffer.append(url+"/BlogDetail/"+id + "\n");
        }
        log.info("发送实时推送百度分录 urllist={}",urlBuffer.toString());
        HttpEntity<String> entity = new HttpEntity<String>(urlBuffer.toString(), headers);
        String result = restTemplate.postForObject(api_url, entity, String.class);
        System.out.println("------------------------------------------------------------------------------");
        log.info("实时推送百度收录的回执：{}",result);
        articleService.updateEntity();
        return  result;
    }

}
