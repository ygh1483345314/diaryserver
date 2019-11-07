package com.diary.main.model;/*
Created by hao on 2019/10/28
*/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class ConstantQiniu {
    private String accessKey;

    private String secretKey;

    private String bucket;

    private String path;

}
