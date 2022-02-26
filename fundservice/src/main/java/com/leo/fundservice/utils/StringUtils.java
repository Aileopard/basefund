package com.leo.fundservice.utils;

/**
 * 功能描述：字符串工具类
 * @author leo-zu
 * @create 2021-05-28 14:42
 */
public class StringUtils {
    /**
     * 功能描述：判断字符串是否为空
     * @param str 字符串
     * @return boolean
     */
    public static boolean isBlank(String str){
        return str == null || "".equals(str);
    }

}
