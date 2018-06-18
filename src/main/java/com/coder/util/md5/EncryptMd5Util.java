package com.coder.util.md5;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class EncryptMd5Util {
    public static String encryptMd5(String password){
        String result = "";

        try {
            MessageDigest md5 =
                    MessageDigest.getInstance("MD5");

            byte[] temp =
                    md5.digest(password.getBytes("utf-8"));

            BASE64Encoder base64en = new BASE64Encoder();

            result = base64en.encode(temp);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }
}
