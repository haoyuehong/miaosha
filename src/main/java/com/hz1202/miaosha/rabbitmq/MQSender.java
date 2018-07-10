package com.hz1202.miaosha.rabbitmq;

import com.hz1202.miaosha.service.RedisService;
import com.hz1202.miaosha.vo.MiaoShaMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    private  AmqpTemplate amqpTemplate;

    /*public void send(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE_NAME,msg);
        System.out.println("send message:"+msg);
    }

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend("topicExchange","topic.key1",msg+"1");//第一个参数代表交换机名 第二个代表满足匹配规则的表达式  第三个消息
        amqpTemplate.convertAndSend("topicExchange","topic.key2",msg+"2");
        System.out.println("send topic message:"+msg);
    }

    public void sendFanout(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg);//第一个参数代表交换机名   第三个消息
        System.out.println("send fanout message:"+msg);
    }

    public void sendHeader(Object message){
        String msg = RedisService.beanToString(message);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("header1","value1");
        messageProperties.setHeader("header2","value2");
        Message obj = new Message(msg.getBytes(),messageProperties);
        amqpTemplate.convertAndSend("headersExchange","",obj);//第一个参数代表交换机名   第三个消息
        System.out.println("send fanout message:"+msg);
    }*/


    public void sendMiaoShaMessage(MiaoShaMessage miaoShaMessage) {
        String msg = RedisService.beanToString(miaoShaMessage);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
        System.out.println("秒杀信息入队:"+msg+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
