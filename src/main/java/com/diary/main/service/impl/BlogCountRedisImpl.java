package com.diary.main.service.impl;/*
Created by hao on 2019/11/1
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



public abstract class BlogCountRedisImpl extends BaseBlogCount {

    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void insert(String key, String value, Integer timeout, TimeUnit timeUnit,Integer id,Integer lookCount) {
        diaryRedisCacheManager.insert(key,value,0,TimeUnit.DAYS);
//        TransportClient transportClient;
//        transportClient.prepareUpdate("twitter", "tweet", "AWQrxpiTF3aJ5qXnAM6l")
//                .setDoc(jsonBuilder()
//                        .startObject()
//                        .field("user", "male2")
//                        .endObject())
//                .get();

//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.index("index");
//        updateRequest.type("type");
//        updateRequest.id("1");
//        updateRequest.doc(jsonBuilder()
//                .startObject()
//                .field("gender", "male")
//                .endObject());
//        template.update(updateRequest).get();


    }


}
