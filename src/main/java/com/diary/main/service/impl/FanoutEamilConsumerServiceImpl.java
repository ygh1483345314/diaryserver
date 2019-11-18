package com.diary.main.service.impl;

import com.diary.main.config.RabbitConfig;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.EmailModel;
import com.diary.main.service.EmailService;
import com.diary.main.service.FanoutEamilConsumerService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = RabbitConfig.FANOUT_EMAIL_QUEUE)
@Slf4j
public class FanoutEamilConsumerServiceImpl implements FanoutEamilConsumerService {

    @Autowired
    private EmailService emailService;

    @RabbitHandler
    @Override
    public void process(String msg) {
        log.info("邮件队列开始发送............msg={}", msg);
        try {
            log.info("邮件队列尝试发送............msg={}", msg);
            Gson gson=new Gson();
            EmailModel emailModel=gson.fromJson(msg, EmailModel.class);
            emailService.sendEmailMessage(emailModel);
            log.info("邮件队列发送成功:msg={}", msg);
        }catch (Exception e){
            log.error("邮件队列消费者程序异常", e.getMessage());
            throw  new DiaryException(MsgEnum.CONSUMER_ERROR);
        }
//        System.out.println(msg);
    }
}
