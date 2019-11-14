package com.diary.main.service.impl;/*
Created by hao on 2019/11/12
*/

import com.diary.main.config.EmailConfig;
import com.diary.main.model.EmailModel;
import com.diary.main.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailConfig emailConfig;
    @Value("${spring.mail.username}")
    private String from;//注入全局配置参数

    @Autowired(required = false)
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailMessage(EmailModel emailModel) {
        SimpleMailMessage simpleMailMessage=emailConfig.getSimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom(from);
        //邮件接收人
        simpleMailMessage.setTo(emailModel.getRecipient());
        //邮件主题
        simpleMailMessage.setSubject(emailModel.getSubject());
        //邮件内容
        simpleMailMessage.setText(emailModel.getContent());
        javaMailSender.send(simpleMailMessage);
    }
}
