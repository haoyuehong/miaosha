package com.hz1202.miaosha.redis;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 14:46 2018/5/3
 */
public abstract class BasePrefix implements KeyPrefix{

    //有效时间
    private int expireSeconds;

    //前缀
    private String prefix;

    public BasePrefix(String prefix) {
       this.expireSeconds = 0;
       this.prefix = prefix;
    }

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
        String simpleName = getClass().getSimpleName();
        return simpleName+":"+prefix;
    }
}
