package com.search.pkgs.util;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import lombok.Data;

/**
 * If you see this, I would like to introduce {@link java.util.concurrent.CompletableFuture} to you.
 * <p>
 * Here is some docs about: https://zhuanlan.zhihu.com/p/101716685.
 * <p>
 * <p>
 * May the code be with you.
 *
 * @author cs12110
 * @version V1.0
 * @since 2020-09-29 10:57
 */
public class ThreadUtil {

    /**
     * 线程工厂
     */
    private static class NamingThreadFactory implements ThreadFactory {

        /**
         * 工厂名称前缀,创建的线程都是按照这个前缀
         */
        private String prefix;
        /**
         * 线程计数器
         */
        private AtomicInteger threadCounter = new AtomicInteger(1);

        public NamingThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName = prefix + threadCounter.getAndIncrement();
            return new Thread(r, threadName);
        }
    }

    /**
     * 通用多线程,带CountDownLatch
     *
     * @param <T> 参数类型
     */
    @Data
    public static class CommonLatchRunnable<T> implements Runnable {
        /**
         * 计数器
         */
        private CountDownLatch countDownLatch;

        /**
         * 消费者
         */
        private Consumer<T> consumer;

        /**
         * 数据
         */
        private T data;

        @Override
        public void run() {
            try {
                consumer.accept(data);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * 通用多线程
     *
     * @param <T> 参数类型
     */
    @Data
    public static class CommonRunnable<T> implements Runnable {

        /**
         * 消费者
         */
        private Consumer<T> consumer;

        /**
         * 数据
         */
        private T data;

        @Override
        public void run() {
            consumer.accept(data);
        }
    }

    /**
     * 创建线程池
     *
     * @param prefix    线程池名称
     * @param threadNum 线程数
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor createFixedThreadPool(String prefix, int threadNum) {
        NamingThreadFactory namingThreadFactory = new NamingThreadFactory(prefix);
        return new ThreadPoolExecutor(threadNum, threadNum, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            namingThreadFactory);
    }

    /**
     * 提交任务(忽略子任务异常)
     *
     * @param poolExecutor 池
     * @param task         任务
     */
    public static void execute(ThreadPoolExecutor poolExecutor, Runnable task) {
        if (Objects.isNull(poolExecutor) || Objects.isNull(task)) {
            throw new IllegalArgumentException("参数异常");
        }
        poolExecutor.execute(task);
    }

    /**
     * 创建线程
     *
     * @param countDownLatch countdownLatch
     * @param consumer       消费者
     * @param value          value
     * @return CommonLatchRunnable
     */
    public static <T> CommonLatchRunnable<T> createLatchRunnable(CountDownLatch countDownLatch, Consumer<T> consumer,
        T value) {
        if (Objects.isNull(countDownLatch) || Objects.isNull(consumer)) {
            throw new IllegalArgumentException("参数不正确");
        }

        CommonLatchRunnable<T> task = new CommonLatchRunnable<>();
        task.setConsumer(consumer);
        task.setData(value);
        task.setCountDownLatch(countDownLatch);

        return task;
    }

    /**
     * 创建线程
     *
     * @param consumer 消费者
     * @param value    value
     * @return CommonRunnable
     */
    public static <T> CommonRunnable<T> createRunnable(Consumer<T> consumer, T value) {
        if (Objects.isNull(consumer)) {
            throw new IllegalArgumentException("参数不正确");
        }

        CommonRunnable<T> task = new CommonRunnable<>();
        task.setConsumer(consumer);
        task.setData(value);

        return task;
    }

    /**
     * 创建countdownLatch
     *
     * @param initNum initNum,初始化开始倒数参数
     * @return CountDownLatch
     */
    public static CountDownLatch createLatch(int initNum) {
        return new CountDownLatch(initNum);
    }

    /**
     * 等待CountDownLatch执行完成
     *
     * @param latch CountDownLatch
     */
    public static void waitingForLatch(CountDownLatch latch) {
        if (Objects.isNull(latch)) {
            return;
        }
        try {
            latch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭线程池
     *
     * @param executor ThreadPoolExecutor
     */
    public static void shutdown(ThreadPoolExecutor executor) {
        if (Objects.isNull(executor)) {
            return;
        }
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * 休眠
     *
     * @param seconds 秒数
     */
    public static void sleepWithSeconds(int seconds) {
        long times = 1000L;
        sleep(seconds * times);
    }

    /**
     * 异步启动consumer
     *
     * @param consumer consumer
     * @param value    value
     */
    @SuppressWarnings("all")
    public static <T> void asyncRun(Consumer<T> consumer, T value) {
        if (Objects.isNull(consumer)) {
            return;
        }

        CommonRunnable<T> runnable = createRunnable(consumer, value);
        new Thread(runnable).start();
    }

    /**
     * 休眠
     *
     * @param mill 毫秒数
     */
    public static void sleep(long mill) {
        try {
            Thread.sleep(mill);
        } catch (Exception e) {
            // do nothing
        }
    }
}
