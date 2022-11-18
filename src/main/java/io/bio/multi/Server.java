package io.bio.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: xjjiang
 * @Data: 2022/11/18 17:55
 * @Description:
 */
public class Server {


    public static void main(String[] args) {
        try {
            ServerSocket ss;
            ss = new ServerSocket(9999);
            while (true) {
                Socket socket = ss.accept();
                new ServerThreadReader(socket).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
