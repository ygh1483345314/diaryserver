package com.diary.main.service.impl;/*
Created by hao on 2019/11/1
*/

import com.diary.main.config.DiaryRedisCacheManager;
import com.diary.main.model.Article;
import com.diary.main.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class BaseBlogCount {
    @Autowired
    public DiaryRedisCacheManager diaryRedisCacheManager;

    @Autowired
    public ArticleService articleService;

    public  static  final String READING_COUNT="blogCount:";


    public  abstract void  updateCountByid(Integer lookCount, Integer qty);
    public abstract void insert(String key, String value, Integer timeout, TimeUnit timeUnit,Integer id,Integer lookCount);

    public   Integer operation(String key,Integer id){
        String blogCountByid=diaryRedisCacheManager.get(key+id);
        Map<String,Integer>mapCount=getOldCountQtyById(blogCountByid,id);
        Integer lookCount=mapCount.get("lookCount")+1;
        Integer qty=mapCount.get("qty")+1;
        if(qty>=10){//10 次浏览更新一次数据库
            updateCountByid(lookCount,id);
            qty=0;//归0
        }
        insert(key+id,lookCount+":"+qty,0, TimeUnit.DAYS,id,lookCount);
        return  lookCount;
    };

    private Map<String,Integer> getOldCountQtyById(String blogCountByid,Integer id){
        Map<String,Integer>map=new HashMap<>();
        String sp[]=blogCountByid.split(":");
        if(sp.length<2){
            Article article =articleService.selectById(id);
            Integer reading=article.getReading();
            if(reading==null){
                reading=0;
            }
            map.put("lookCount",reading);
            map.put("qty",0);
            return  map;
        }
        Integer lookCount=Integer.valueOf(sp[0]);//浏览量
        Integer qty=Integer.valueOf(sp[1]);//统计浏览数 每过10次 写入mysql中
        map.put("lookCount",lookCount);
        map.put("qty",qty);
        return  map;
    }

}
