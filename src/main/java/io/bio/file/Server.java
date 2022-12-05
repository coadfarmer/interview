package io.bio.file;


import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 11:47
 * @Description: 服务端开发，实现接受客户端任意类型文件，并保存到服务端磁盘
 */
public class Server {

    public static void main(String[] args) {
        try ( ServerSocket ss = new ServerSocket(8888)){
           while (true){
               Socket socket = ss.accept();
               //交给一个独立的线程来处理与这个客户端的文件通信需求
               new ServerReaderThread(socket).start();
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
