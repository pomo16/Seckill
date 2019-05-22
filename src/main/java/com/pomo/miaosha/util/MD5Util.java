package com.pomo.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    /**
     * MD5操作
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 固定盐值
     */
    private static final String salt = "1a2b3c4d";

    /**
     * 第一次MD5，用户输入->表单，pass = md5(明文 + 固定salt)，防止明文密码传输泄露
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次MD5，表单->数据库，pass = md5(用户输入 + 随机salt)，防止数据库被盗破解密码
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456")); //12123456c3
        //System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
