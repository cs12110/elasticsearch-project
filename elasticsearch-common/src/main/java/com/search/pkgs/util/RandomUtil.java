package com.search.pkgs.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 10:18
 */
public class RandomUtil {

    private static AtomicInteger counter = new AtomicInteger(1);

    public static String timestamp() {
        String countIndex = String.format("%05d", counter.getAndIncrement());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date()) + countIndex;
    }
}
