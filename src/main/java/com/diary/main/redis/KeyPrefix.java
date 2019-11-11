package com.diary.main.redis;/*
Created by hao on 2019/11/8
*/

import java.util.concurrent.TimeUnit;

public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
    public TimeUnit getTimeUnit();

}
