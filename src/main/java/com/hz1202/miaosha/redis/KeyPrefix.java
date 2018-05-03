package com.hz1202.miaosha.redis;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 14:44 2018/5/3
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
