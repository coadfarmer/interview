package io.bio.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @Author: xjjiang
 * @Data: 2022/11/23 15:54
 * @Description:
 */
public class ServerReaderThread extends Thread {

    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("服务端开始接收...");

        try {
            //1.得到数据输入流读取客户端发送过来的数据
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            //2.读取客户端发送过来的文件类型
            String suffix = dis.readUTF();
            System.out.println("服务端已经成功接收到了："+ suffix +"类型文件");
            //3.定义一个字节输出管道负责把客户端发来的文件数据写出去
            try (OutputStream os = new FileOutputStream("C:\\Users\\38293\\Pictures\\server\\" + UUID.randomUUID() + suffix)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = dis.read(buffer)) > 0) {
                    os.write(buffer, 0, len);
                }
            }
            System.out.println("服务端接收文件保存成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
