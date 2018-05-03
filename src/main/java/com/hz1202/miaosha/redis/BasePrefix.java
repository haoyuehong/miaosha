package com.hz1202.miaosha.redis;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 14:46 2018/5/3
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
