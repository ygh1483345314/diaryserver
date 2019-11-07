package com.diary.main.service.impl;/*
Created by hao on 2019/11/4
*/
import org.springframework.stereotype.Service;

@Service("blogCountImpl")
public class BlogCountImpl  extends  BlogCountRedisImpl{
    @Override
    public void updateCountByid(Integer lookCount, Integer qty) {
        articleService.updateReadingById(lookCount, qty);
    }


}
