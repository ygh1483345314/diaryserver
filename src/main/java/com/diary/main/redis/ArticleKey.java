package com.diary.main.redis;/*
Created by hao on 2019/11/8
*/

import java.util.concurrent.TimeUnit;

public class ArticleKey extends  BaseRedisKey {
    public ArticleKey(String prefix) {
        super(prefix);
    }

    public ArticleKey(String prefix, Class classzz) {
        super(prefix, classzz);
    }

    public ArticleKey(String prefix, Integer expireSeconds, TimeUnit timeUnit, Class classzz) {
        super(prefix, expireSeconds,timeUnit, classzz);
    }



}
