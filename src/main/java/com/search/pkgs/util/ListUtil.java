package com.search.pkgs.util;

import java.util.List;
import java.util.Objects;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 13:44
 */
public class ListUtil {

    public static boolean isEmpty(List<?> list) {
        return Objects.isNull(list) || list.isEmpty();
    }

    public static <T> T getFirstElement(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }
}
