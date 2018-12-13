package com.xxlllq.dataprovider.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * @类名称： MD5Util
 * @类描述：MD5工具类
 * @创建人：xiangxl
 * @创建时间：2018/12/13 12:54
 * @version：
 */
public class MD5Util {

    /**
     * 普通MD5
     *
     * @param input
     * @return
     */
    public static String MD5(String input) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "check jdk";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = input.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 生成盐值
     *
     * @return
     */
    public static String generateSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 生成系统中的用户加密后的密码
     *
     * @param code     用户账号
     * @param password 加密前的密码
     * @param salt     盐
     * @return
     */
    public static String generateUserPassword(String code, String password, String salt) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(salt))
            return null;

        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + (code + salt).getBytes("UTF-8"));
    }

}