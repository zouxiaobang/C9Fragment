package com.cloud.tool.hash;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 哈希计算工具类：MD5/SHA1/SHA256
 */
public class HashUtil {

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9','A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 生成文件的md5校验值
     */
    public static String fileMd5(String filePath) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try{
            fis = new FileInputStream(filePath);
            md5 = MessageDigest.getInstance("MD5");
            while((numRead=fis.read(buffer)) > 0) {
                md5.update(buffer,0,numRead);
            }
            fis.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    private static String hashString(String inStr, String hashFunction) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(hashFunction);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static String md5(String input) {
        return hashString(input, "MD5");
    }

    public static String sha1(String input) {
        return hashString(input, "SHA-1");
    }

    public static String sha256(String input) {
        return hashString(input, "SHA-256");
    }
}
