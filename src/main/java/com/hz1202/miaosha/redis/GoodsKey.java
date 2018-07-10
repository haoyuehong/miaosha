package com.hz1202.miaosha.redis;

public class GoodsKey extends BasePrefix {

    private GoodsKey(String prefix,int expireSeconds) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList =new GoodsKey("gl",60);
    public static GoodsKey getGoodsDetail =new GoodsKey("gd",60);
    public static GoodsKey getMiaoShaGoodsStock =new GoodsKey("gs",0);

}
