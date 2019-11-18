package com.diary.main.service;

public interface FanoutProducerService {

    void send(String queueName,String msg);


}
