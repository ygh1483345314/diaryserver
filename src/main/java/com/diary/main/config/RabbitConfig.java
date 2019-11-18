package com.diary.main.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    // 邮件队列
    public final static String FANOUT_EMAIL_QUEUE = "fanout_eamil_queue";


    // 短信队列
    public final static  String EXCHANGE_NAME = "fanoutExchange";

    // 1.定义队列邮件
    @Bean
    public Queue fanOutEamilQueue() {
        return new Queue(FANOUT_EMAIL_QUEUE);
    }

    // 2.定义交换机
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }
    // 3.队列与交换机绑定邮件队列
    @Bean
    Binding bindingExchangeEamil(Queue fanOutEamilQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOutEamilQueue).to(fanoutExchange);
    }



}
