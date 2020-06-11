package com.tenfar.yiyi.util;

import java.util.UUID;

/**
 * UUID工具类
 **/
public class UUIDUtil {

    /**
     * 获得去掉“-”符号的UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[+]", "");
    }

    /**
     * 去掉“字母”和“-”的UUID
     *
     * @param count
     */
    public static String getUUID(int count) {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "").substring(0, count);
    }

    public static void main(String[] args) {
        System.out.println(getUUID(15));
    }

}
