package com.search.pkgs.events;

import com.google.common.eventbus.AsyncEventBus;
import com.search.pkgs.util.ThreadUtil;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-20 09:58
 */
@SuppressWarnings("all")
public class SysEventPublisher {

    private static final String EVENT_BUS_NAME = "canal-event-pool";
    private static final ThreadPoolExecutor THREAD_POOL = ThreadUtil.createFixedThreadPool(EVENT_BUS_NAME, 5);
    private static AsyncEventBus asyncEventBus = new AsyncEventBus(THREAD_POOL);

    public static void publish(Object event) {
        if (Objects.isNull(event)) {
            return;
        }
        asyncEventBus.post(event);
    }

    public static void register(Object listener) {
        if (Objects.isNull(listener)) {
            return;
        }
        asyncEventBus.register(listener);
    }
}
