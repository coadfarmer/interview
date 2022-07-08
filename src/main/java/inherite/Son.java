package main.java.inherite;

public class Son extends Dad {

    private int age;

    private String name;

    public Son() {
        System.out.println("构造函数");
    }

    void init() {
        System.out.println("init");
    }
}
