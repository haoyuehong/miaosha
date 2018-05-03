package com.hz1202.miaosha.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 12:00 2018/5/3
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisConfig {
    private String host;
    private Integer port;
    private Integer timeout;
    private String password;
    private Integer poolMaxTotal;
    private Integer poolMaxIdle;
    private Integer poolMaxWait;


    @Bean
    public JedisPool jedisPoolFactroy(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMaxTotal(poolMaxTotal);
        poolConfig.setMaxWaitMillis(poolMaxWait*1000);
        JedisPool jedisPool = new JedisPool(poolConfig,host,port,timeout*1000,password,0);
        return jedisPool;
    }
}





















