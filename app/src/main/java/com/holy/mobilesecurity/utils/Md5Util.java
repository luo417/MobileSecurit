package com.holy.mobilesecurity.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Holy on 2016/5/1.
 */
public class Md5Util {
    public static String changeToMD5Code(String password){
        password = password + "dahai.417";  //加盐处理
        try {
            //1.制定加密算法类型
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //2.将需要加密的字符串装换为byte数组，然后进行随机哈希
            byte[] bytes = md5.digest(password.getBytes());
            //3.循环遍历bytes，然后让其生成32位字符串，固定写法
            //4.拼接字符串
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length()<2){
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
