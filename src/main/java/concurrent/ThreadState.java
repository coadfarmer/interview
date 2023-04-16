package concurrent;

/**
 * @Author: xjjiang
 * @Data: 2022/5/18 17:11
 * @Description: 描述六种线程状态
 */
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread t1 is running...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread t1 is done.");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread t2 is running...");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread t2 is done.");
        });

        // 启动线程t1和t2
        t1.start();
        t2.start();

        // 主线程等待线程t1和t2执行完毕
        t2.join();
        t1.join();


        // 线程t1和t2执行完毕后再执行此处代码
        System.out.println("All threads are done.");
    }//TERMINATED

}
