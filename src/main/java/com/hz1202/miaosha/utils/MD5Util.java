package com.hz1202.miaosha.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5String(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String inputPassToFormPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5String(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5String(str);
    }



    public static String inputPassToDBPass(String inputPasss,String saltDB){
        String formPass = inputPassToFormPass(inputPasss);

        return formPassToDBPass(formPass,saltDB);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
