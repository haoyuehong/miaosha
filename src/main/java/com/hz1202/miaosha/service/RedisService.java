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

    /**
     * redis get方法（该工具中为了保证所有key都唯一  所以给每一个key都加了前缀）
     * @param prefix 前缀
     * @param key key值
     * @param clazz 获取后要转换成的对象类型
     * @return 转换后的对象
     */
    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            //获取jedis
            jedis = jedisPool.getResource();
            //获取真正的key
            String realKey = prefix.getPrefix()+key;
            String str = jedis.get(realKey);
            //将获取到的String转换成clazz对象
            T t = StringToBean(str,clazz);
            return t;
        }finally {
            //完成后回收资源
            returnToPool(jedis);
        }
    }

    /**
     * redis set方法（该工具中为了保证所有key都唯一  所以给每一个key都加了前缀）
     * @param prefix 前缀
     * @param key key值
     * @param value 要保存的数据
     */
    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try {
            //获取jedis
            jedis = jedisPool.getResource();
            //将对象转换为字符串
            String str = beanToString(value);
            if(str == null || str.length() <= 0){
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix()+key;
            //获取key有效时间
            int seconds = prefix.expireSeconds();
            //如果有效时间小于等于0，说名该key值永久有效，否则在保存数据时需要加入有效时间
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

    /**
     * 判断key是否存在
     * @param prefix 前缀
     * @param key key值
     * @return
     */
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


    /**
     * 给当前的key对应的value + 1
     * @param prefix 前缀
     * @param key key值
     * @return
     */
    public Boolean incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix()+key;
            jedis.incr(realKey);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 给当前key对应的value - 1
     * @param prefix 前缀
     * @param key key
     * @return
     */
    public Boolean decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix()+key;
            jedis.decr(realKey);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将对象转换为字符串
     * @param value
     * @param <T>
     * @return
     */
    public static  <T> String beanToString(T value) {
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

    /**
     * 将String 转换为对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T StringToBean(String str,Class<T> clazz) {
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

    /**
     * 回收redis资源
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }
}
