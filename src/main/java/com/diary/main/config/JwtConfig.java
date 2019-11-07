package com.diary.main.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private  Integer expiration;//到期时间
    private  String secret;//签名
    private  Integer refresh;//续签时间
}
