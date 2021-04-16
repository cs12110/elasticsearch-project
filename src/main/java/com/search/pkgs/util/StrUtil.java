package com.search.pkgs.util;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 13:43
 */
public class StrUtil {

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }
}
