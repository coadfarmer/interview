package designPattern.singleton;

/**
 * {@code @Author:} xjjiang
 * {@code @Data:} 2023/4/13 11:42
 * {@code @Description:} 单例设计模式----饿汉式
 */
public class Singleton {
    private static final Singleton INSTANCE;

    static {
        INSTANCE = new Singleton();
    }

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

