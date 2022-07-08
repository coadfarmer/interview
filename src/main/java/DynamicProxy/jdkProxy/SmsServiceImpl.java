package main.java.DynamicProxy.jdkProxy;


/**
 * 2.实现发送短信的接口
 */
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
