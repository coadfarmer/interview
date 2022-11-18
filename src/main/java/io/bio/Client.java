package io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: xjjiang
 * @Data: 2022/11/18 16:34
 * @Description:
 */
public class Client {

    public static void main(String[] args) throws IOException {

        //1.创建Socket对象请求服务器的链接
        Socket socket = new Socket("127.0.0.1", 9999);
        //2.从Socket对象中获取一个字节输出流
        OutputStream os = socket.getOutputStream();
        //3.把字节输出流包装成一个打印流
        PrintStream ps = new PrintStream(os);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("请说：");
            String msg = scanner.nextLine();
            ps.println(msg);
            ps.flush();
        }

    }

}
