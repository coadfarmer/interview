package io.bio.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 10:29
 * @Description:
 */
public class ServerRunnableTarget implements Runnable{
    private Socket socket;

    public ServerRunnableTarget(Socket socket){
        this.socket = socket;
    }

    /**
     * 处理客户端socket通信需求
     */
    @Override
    public void run() {
        try {
            InputStream is  = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg=br.readLine())!=null){
                System.out.println("服务端收到：" + msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
