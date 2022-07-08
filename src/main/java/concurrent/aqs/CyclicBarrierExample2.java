package main.java.concurrent.aqs;

import java.util.concurrent.*;

public class CyclicBarrierExample2 {
  // 请求的数量
  private static final int threadCount = 550;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

  public static void main(String[] args) {
    // 创建线程池
    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    for (int i = 0; i < threadCount; i++) {
      final int threadNum = i;
      threadPool.execute(() -> {
        try {
          test(threadNum);

        } catch (InterruptedException | BrokenBarrierException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });
    }
    threadPool.shutdown();
  }

  public static void test(int threadnum) throws InterruptedException, BrokenBarrierException {
    System.out.println("threadnum:" + threadnum + "is ready");
    try {
      //进入线程等待，最多等待100s，等线程数到达5之后开始执行
      cyclicBarrier.await(100, TimeUnit.SECONDS);
    } catch (Exception e) {
      System.out.println("-----CyclicBarrierException------");
    }
    System.out.println("threadnum:" + threadnum + "is finish");
  }

}
