package concurrent.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xjjiang
 * @Data: 2022/5/19 17:10
 * @Description: 多线程创建工具类
 */
public class MultithreadingUtil {

    //线程池的核心线程数量
    private static int CORE_POOL_SIZE = 5;
    //线程池的最大线程数
    private static int MAX_POOL_SIZE = 10;
    //任务队列，用来储存等待执行任务的队列
    private static int QUEUE_CAPACITY = 20;
    //当线程数大于核心线程数时，多余的空闲线程存活的最长时间
    private static long KEEP_ALIVE_TIME = 1L;

    private final ThreadPoolExecutor executor;

    public MultithreadingUtil(int CORE_POOL_SIZE,
                              int MAX_POOL_SIZE,
                              int QUEUE_CAPACITY,
                              long KEEP_ALIVE_TIME) {
        MultithreadingUtil.CORE_POOL_SIZE = CORE_POOL_SIZE;
        MultithreadingUtil.MAX_POOL_SIZE = MAX_POOL_SIZE;
        MultithreadingUtil.QUEUE_CAPACITY = QUEUE_CAPACITY;
        MultithreadingUtil.KEEP_ALIVE_TIME = KEEP_ALIVE_TIME;
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 开启多线程
     *
     * @param runnable    线程任务
     * @param threadCount 线程数
     */
    public void enableMultithreading(Runnable runnable, int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            executor.execute(runnable);
        }
        executor.shutdown();
    }


}
