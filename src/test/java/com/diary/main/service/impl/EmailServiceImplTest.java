package com.diary.main.service.impl;

import com.diary.main.model.EmailModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/*
Created by hao on 2019/11/12
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmailServiceImplTest {

    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private  EmailModel emailModel;
    @Test
    public void sendEmailMessage() {
        emailModel.setSubject("博客消息提醒:");
        emailModel.setContent("内容");
        emailService.sendEmailMessage(emailModel);
    }
}