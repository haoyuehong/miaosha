package com.hz1202.miaosha.redis;

public class MiaoShaKey extends BasePrefix{

    private MiaoShaKey(String prefix) {
        super(prefix);
    }

    public static MiaoShaKey isGoodsOver = new MiaoShaKey("go");
}
