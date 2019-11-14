package com.diary.main.config;/*
Created by hao on 2019/11/12
*/

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
    public SimpleMailMessage getSimpleMailMessage(){
        return  new SimpleMailMessage();
    }

}
