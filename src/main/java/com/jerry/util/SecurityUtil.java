package com.jerry.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * secureUtil
 *
 * @author jerrychien
 * @create 2016-08-12 23:41
 */
public class SecurityUtil {

    public static final String MD5 = "MD5";

    public static final String SHA1 = "SHA-1";

    public static final String SHA256 = "SHA-256";

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(uuid.substring(0, 8));
        sb.append(uuid.substring(9, 13));
        sb.append(uuid.substring(14, 18));
        sb.append(uuid.substring(19, 23));
        sb.append(uuid.substring(24));
        return sb.toString();
    }

    public static String md5(String source) {
        return digest(source, MD5);
    }

    /**
     * @param source
     * @param algorithm
     * @return
     */
    public static String digest(String source, String algorithm) {
        String result = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(source.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            result = bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //把byte转为16进制字符, 先把b和0xFF按位与操作后,得到一个int类型的值
            String temp = Integer.toHexString(0xFF & b);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexStr) {
        int len = hexStr.length();
        hexStr = hexStr.toUpperCase();
        byte[] des;
        if (len % 2 != 0 || len == 0) {
            return null;
        } else {
            int halfLen = len / 2;
            des = new byte[halfLen];
            char[] tempChars = hexStr.toCharArray();
            for (int i = 0; i < halfLen; ++i) {
                char c1 = tempChars[i * 2];
                char c2 = tempChars[i * 2 + 1];
                int tempI = 0;
                if (c1 >= '0' && c1 <= '9') {
                    tempI += ((c1 - '0') << 4);
                } else if (c1 >= 'A' && c1 <= 'F') {
                    tempI += (c1 - 'A' + 10) << 4;
                } else {
                    return null;
                }
                if (c2 >= '0' && c2 <= '9') {
                    tempI += (c2 - '0');
                } else if (c2 >= 'A' && c2 <= 'F') {
                    tempI += (c2 - 'A' + 10);
                } else {
                    return null;
                }
                des[i] = (byte) tempI;
                // System.out.println(des[i]);
            }
            return des;

        }
    }

    public static void main(String[] args) {
        String uuid = SecurityUtil.getUUID();
        System.out.println(uuid.length());
        System.out.println(SecurityUtil.digest("xxxxx", SecurityUtil.SHA1));
        System.out.println(Integer.toHexString(72));
        byte a = 15;
        int b = 0xFF;
        int cc = 0xFF & a;
        System.out.println(cc);
    }
}
