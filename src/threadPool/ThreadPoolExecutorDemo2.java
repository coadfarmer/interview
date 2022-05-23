package threadPool;

import concurrent.utils.MultithreadingUtil;

public class ThreadPoolExecutorDemo2 {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 20;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {

        MultithreadingUtil multithreading = new MultithreadingUtil(CORE_POOL_SIZE, MAX_POOL_SIZE, QUEUE_CAPACITY, KEEP_ALIVE_TIME);
        multithreading.enableMultithreading(() -> {
            System.out.println(Thread.currentThread());
        }, 10);

    }
}

