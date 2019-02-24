package com.cloud.tool.util.random;

import java.util.Random;

/**
 * @Author: xb.zou
 * @Date: 2019/2/23
 * @Desc: to-> 随机生成数据工具类
 */
public class RandomTool {
    private static Random sRandom = new Random();

    /**
     * 获取指定长度的随机数
     * 一般可用于当做验证码等
     */
    public static String getRandomNum(int size) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(String.valueOf(sRandom.nextInt(10)));
        }

        return sb.toString();
    }

    /**
     * 获取指定长度的随机字符串
     */
    public static String getStringRandom(int size) {
        StringBuilder stringBuilder = new StringBuilder();

        //参数length，表示生成几位随机数
        for (int i = 0; i < size; i++) {

            String charOrNum = sRandom.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = sRandom.nextInt(2) % 2 == 0 ? 65 : 97;
                stringBuilder.append((char) (sRandom.nextInt(26) + temp));
            } else {
                stringBuilder.append(String.valueOf(sRandom.nextInt(10)));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取指定最大和最小数的随机数字
     */
    public static Integer getIntegerRandom(int min, int max) {
        return min + sRandom.nextInt(max - min + 1);
    }
}
