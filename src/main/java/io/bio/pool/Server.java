package io.bio.pool;

import org.junit.internal.runners.statements.RunAfters;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 10:26
 * @Description: 利用线程池实现伪异步
 */
public class Server {

    public static void main(String[] args) {
        System.out.println("==服务端启动==");
        try {
            ServerSocket ss = new ServerSocket(9999);
            HandlerSocketServerPool pool = new HandlerSocketServerPool(3,10);
            while (true){
                Socket socket = ss.accept();
                //把socket封装成任务对象
                Runnable target = new ServerRunnableTarget(socket);
                pool.execute(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
