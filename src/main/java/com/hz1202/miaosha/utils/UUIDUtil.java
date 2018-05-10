package com.hz1202.miaosha.utils;

import java.util.UUID;

/**
 * @Author: mol
 * @Description:
 * @Date: create in 9:56 2018/5/7
 */
public class UUIDUtil {

    public static String getUUID(){

        return UUID.randomUUID().toString().replace("-","");
    }
}
