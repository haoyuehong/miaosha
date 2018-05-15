package com.hz1202.miaosha.redis;


/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:59 2018/5/7
 */
public class MiaoShaUserKey extends BasePrefix {

    public static int USER_EXPIRESECONDSL = 3600 * 2;

    private MiaoShaUserKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }
    public static MiaoShaUserKey userToken = new MiaoShaUserKey(USER_EXPIRESECONDSL,"userToken");
    public static MiaoShaUserKey getById = new MiaoShaUserKey(0,"id");
}
