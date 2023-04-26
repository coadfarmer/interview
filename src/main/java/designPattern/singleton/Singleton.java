package designPattern.singleton;

/**
 * {@code @Author:} xjjiang
 * {@code @Data:} 2023/4/13 11:42
 * {@code @Description:} 单例设计模式----饿汉式
 */
public class Singleton {

    private Singleton(){}

    private static final class InstanceHolder {
        static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return InstanceHolder.INSTANCE;
    }

}

