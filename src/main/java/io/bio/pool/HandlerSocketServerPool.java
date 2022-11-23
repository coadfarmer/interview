package io.bio.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 10:20
 * @Description: 线程池
 */
public class HandlerSocketServerPool {

    //1.创建一个线程池的成员变量用于存储一个线程池对象
    private ExecutorService executorService;

    /**
     * 2.创建这个类的对象的时候就需要初始化线程池对象
     */
    public HandlerSocketServerPool(int maxThreadNum, int queueSize){
        executorService = new ThreadPoolExecutor(3,maxThreadNum,120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    /**
     * 3.提供一个方法来提交任务给线程池的任务队列来暂存，等待线程池处理
     */
    public void execute(Runnable target){
        executorService.execute(target);
    }


}
