package com.hz1202.miaosha.redis;

public class OrderKey extends BasePrefix {


    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getMiaoShaOrderByUidGId = new OrderKey("moug");


}
