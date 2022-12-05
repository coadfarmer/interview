package io.bio.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 11:37
 * @Description: 文件上传客户端
 */
public class Clinet {
    public static void main(String[] args) {
        try (
                InputStream is = new FileInputStream("C:\\Users\\38293\\Pictures\\R.png");
        ) {
            //请求与服务端的Socket连接
            try (Socket socket = new Socket("127.0.0.1", 8888)) {
                //把字节输出流包装成一个数据输出流
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                //先发送文件后缀给服务端
                dos.writeUTF(".png");
            /*
              把文件数据发送给服务端进行接收
             */
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    dos.write(buffer, 0, len);
                }
                dos.flush();
                socket.shutdownOutput();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
