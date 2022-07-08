package main.java.threadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 20;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {

        //使用阿里巴巴推荐的创建线程池的方式
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,//线程池的核心线程数量
                MAX_POOL_SIZE,//线程池的最大线程数
                KEEP_ALIVE_TIME,//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                TimeUnit.SECONDS,//时间单位
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),//任务队列，用来储存等待执行任务的队列
                new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略，当提交的任务过多而不能及时处理时，我们可以定制策略来处理任务

        for (int i = 0; i < 50; i++) {
            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
            Runnable worker = new MyRunnable("" + i);
            //执行Runnable
            executor.execute(worker);
        }
        //终止线程池
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}

