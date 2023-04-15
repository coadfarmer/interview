package designPattern.singleton;

/**
 * @Author: xjjiang
 * @Data: 2023/4/13 11:49
 * @Description: 单例测试
 */
public class Main {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);

        SingletonDoubleCheck s3 = SingletonDoubleCheck.getInstance();
        SingletonDoubleCheck s4 = SingletonDoubleCheck.getInstance();
        System.out.println(s3 == s4);

    }

}
