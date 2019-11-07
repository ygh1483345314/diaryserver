package com.diary.main.config;/*
Created by hao on 2019/10/31
*/
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DiaryRedisCacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    /* 插入数据或者更新数据 */
    public void insert(String key, Object value, long timeout, TimeUnit timeUnit) {

        if (StringUtils.isBlank(key) || !ObjectUtils.anyNotNull(value)) {
            return;
        }
        if (timeout == 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }

    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    public String get(String key){
        String stringValue = String.valueOf(redisTemplate.opsForValue().get(key));
        return  stringValue;
    }


}
