package com.xxxx.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/3/3 19:52
 * @Description:
 *              md5 工具类
 */
@Component
public class MD5Util {

    /**
     * 传入参数加密
     * @param src
     * @return
     */
    public static String md5(String src){
        return DigestUtils.md5Hex(src) ;
    }
    // 自定义初始盐
    private static final String salt = "1a2b3c4d" ;

    public static String inputPassToFromPass(String inputPass){
        //第一次初始混淆密码： 12+pass+3b
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(4) + salt.charAt(3) ;
        return md5(str) ;
    }

    /**
     * 第二次加密到db
     * @return
     */
    public static String fromPassToDBPass(String formPass , String salt){
        String str = salt.charAt(0) + salt.charAt(2)+ formPass+ salt.charAt(4) + salt.charAt(3) ;
        return md5(str) ;
    }

    public static String inputPassToDBPass(String inputPass , String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass ;
    }

    //测试
    public static void main(String[] args) {
        //333560dab33247bda70a8a7d1095409f
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("333560dab33247bda70a8a7d1095409f","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
