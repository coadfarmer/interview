package jvm;

/**
 * @Author: xjjiang
 * @Data: 2023/6/7 11:55
 * @Description:
 */
public class Main {
    public static void main(String[] args) {

// 浅拷贝示例
        Person person1 = new Person("John");
        Person person2 = person1;  // 浅拷贝，person2和person1引用同一个对象

        System.out.println(person1.getName());  // 输出: John
        System.out.println(person2.getName());  // 输出: John

        person2.setName("Alice");  // 修改person2的名字

        System.out.println(person1.getName());  // 输出: Alice，修改person2的名字也影响到了person1

// 深拷贝示例
        Person person3 = new Person("John");
        Person person4 = new Person(person3.getName());  // 深拷贝，创建了一个新的Person对象，并复制了name属性的值

        System.out.println(person3.getName());  // 输出: John
        System.out.println(person4.getName());  // 输出: John

        person4.setName("Alice");  // 修改person4的名字

        System.out.println(person3.getName());  // 输出: John，修改person4的名字不会影响到person3

    }

}
