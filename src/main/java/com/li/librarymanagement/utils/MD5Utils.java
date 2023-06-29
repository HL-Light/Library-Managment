package com.li.librarymanagement.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
    private static final String PASS_SALT = "li";

    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue+ PASS_SALT).getBytes()));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String md5 = null;
        try {
            md5 = getMD5Str("12345");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        System.out.println(md5);
    }
}
