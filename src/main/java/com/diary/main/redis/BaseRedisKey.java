package com.diary.main.redis;/*
Created by hao on 2019/11/8
*/

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public abstract  class  BaseRedisKey <T>  implements  KeyPrefix {
    private Integer expireSeconds;
    private  String prefix;
    private TimeUnit timeUnit;
    private  Class<T> classzz;

    public BaseRedisKey(String prefix){
        this.expireSeconds=0;
        this.prefix=prefix;
    }
    public BaseRedisKey(String prefix,Class<T> classzz){
        this.expireSeconds=0;
        this.prefix=prefix;
        this.classzz=classzz;
    }
    public BaseRedisKey(String prefix,Integer expireSeconds,TimeUnit timeUnit,Class<T> classzz){
        this.expireSeconds=expireSeconds;
        this.prefix=prefix;
        this.classzz=classzz;
        this.timeUnit=timeUnit;
    }


    public T getStrtoBean(String keyStr){
        Gson gson=new Gson();
        T t=gson.fromJson(keyStr,classzz);
        return t;
    }

    @Override
    public int expireSeconds() {
        return this.expireSeconds;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }


    @Override
    public String getPrefix() {
        String className=this.classzz.getSimpleName();
        return className+":"+this.prefix;
    }



}
