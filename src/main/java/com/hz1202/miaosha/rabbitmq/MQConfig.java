package com.hz1202.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);//第一个参数是队列名  第二是是否持久化
    }
}
