package com.diary.main.service.impl;

import com.diary.main.service.FanoutProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FanoutProducerServiceImpl implements FanoutProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(String queueName, String msg) {
        amqpTemplate.convertAndSend(queueName, msg);
    }
}
