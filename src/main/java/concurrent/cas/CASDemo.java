package main.java.concurrent.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xjjiang
 * @Data: 2022/5/16 16:00
 * @Description: CAS模拟多个线程累加count
 */
public class CASDemo {

    /**
     * 线程数
     */
    private static final int threadCount = 20;

    private volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        //每个线程加1000
        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        threadPool.shutdown();
        System.out.println(count);


    }


    public static void increase() {
        /*
         *  compareAndSwap()方法进行判断，模拟自旋操作，如果是false就一直轮询，当且仅当期望值与compareAndSwap中的期望值相等时进行修改
         */
        while (!compareAndSwap(count, count + 1)) {
//            自旋，无操作
        }

    }

    static synchronized boolean compareAndSwap(int expected, int newCount) {
        if (count == expected) {
//            System.out.println("expected:" + expected);
            count = newCount;
            return true;
        }
        return false;
    }

    public static int getCount() {
        return count;
    }

}
