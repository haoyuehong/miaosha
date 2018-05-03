package com.hz1202.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.hz1202.miaosha.redis.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 13:19 2018/5/3
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //获取真正的key
            String realKey = prefix.getPrefix()+key;
            String str = jedis.get(realKey);
            T t = StringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0){
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix()+key;
            int seconds = prefix.expireSeconds();
            if(seconds <= 0){
                jedis.set(realKey,str);
            }else{
                jedis.setex(realKey,seconds,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public Boolean exists(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix()+key;
            jedis.exists(realKey);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return  value+"";
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == Long.class || clazz == long.class){
            return  value+"";
        }else{
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T StringToBean(String str,Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return  (T)Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T)str;
        }else if(clazz == Long.class || clazz == long.class){
            return  (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }
}
