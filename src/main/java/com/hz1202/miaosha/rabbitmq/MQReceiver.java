package com.hz1202.miaosha.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE_NAME)
    public void receiver(String message){
        System.out.println("receiveMessage:"+message);
    }
}
