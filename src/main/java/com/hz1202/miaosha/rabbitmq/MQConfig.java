package com.hz1202.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "queue";
    public static final String TOPIC_QUEUE_NAME1 = "topic.queue1";
    public static final String TOPIC_QUEUE_NAME2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";

    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    /**
     * direct 模式
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);//第一个参数是队列名  第二是是否持久化
    }

    @Bean
    public Queue miaoshaQueue(){
        return new Queue(MIAOSHA_QUEUE,true);//第一个参数是队列名  第二是是否持久化
    }
    /**
     * topic 模式
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue("topic.queue1",true);//第一个参数是队列名  第二是是否持久化
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("topic.queue2",true);//第一个参数是队列名  第二是是否持久化
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding topicBinding(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    /**
     * fanout模式
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * header模式
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange("headersExchange");
    }

    @Bean
    public Queue headersQueue(){
        return new Queue("header.queue",true);//第一个参数是队列名  第二是是否持久化
    }

    @Bean
    public Binding headersBinding(){
        Map<String,Object> map = new HashMap<>();
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }





}
