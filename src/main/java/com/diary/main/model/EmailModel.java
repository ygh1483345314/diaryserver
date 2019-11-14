package com.diary.main.model;/*
Created by hao on 2019/11/12
*/

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailModel {
    @Value("${spring.mail.to}")
    private String recipient;   //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

    public EmailModel() {
        System.out.println(recipient);
//        this.recipient="1936705930@qq.com";
    }
}
