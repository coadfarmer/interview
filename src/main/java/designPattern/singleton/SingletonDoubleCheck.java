package designPattern.singleton;

/**
 * @Author: xjjiang
 * @Data: 2023/4/13 12:10
 * @Description: 双重检验
 */
public class SingletonDoubleCheck {

    private static volatile SingletonDoubleCheck instance;

    private SingletonDoubleCheck(){};

    public static SingletonDoubleCheck getInstance(){
        if(instance == null){
            synchronized (SingletonDoubleCheck.class){
                if(instance == null){
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }

}
