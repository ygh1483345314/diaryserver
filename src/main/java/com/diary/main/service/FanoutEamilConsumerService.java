package com.diary.main.service;

public interface FanoutEamilConsumerService {
    void process(String msg);
}
