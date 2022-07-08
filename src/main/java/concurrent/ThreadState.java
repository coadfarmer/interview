package main.java.concurrent;

/**
 * @Author: xjjiang
 * @Data: 2022/5/18 17:11
 * @Description: 描述六种线程状态
 */
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {

        //NEW
        Thread thread = new Thread(() -> {
            System.out.println("NEW-------" + Thread.currentThread());
            System.out.println("1111111111");
            System.out.println("222222222222");
        });
        System.out.println(thread.getName() + "state" + thread.getState());

        //RUNNABLE
        thread.start();

        //BLOCKED
        new Thread(() -> {
            synchronized ("a") {
                System.out.println("BLOCKED------" + Thread.currentThread());
                System.out.println("333333333333");
                System.out.println("4444444444444");
            }
        }).start();

        //WAITING
        thread.join();

        //TIME_WAITING


    }//TERMINATED

}
