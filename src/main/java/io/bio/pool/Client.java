package io.bio.pool;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: xjjiang
 * @Data: 2022/11/18 17:59
 * @Description: 多请求多接收客户端
 */
public class Client {

    public static void main(String[] args) {

        try {
            //1.请求与服务端Socket对象链接
            Socket socket = new Socket("127.0.0.1",9999);
            //2.得到一个打印流
            PrintStream ps = new PrintStream(socket.getOutputStream());
            //3.使用循环不断的发送消息给服务端
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            while (!msg.equals("/exit")){
                System.out.print("请说:");
                msg = scanner.nextLine();
                ps.println(msg);
                ps.flush();
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
