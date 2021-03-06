## java基础

### 一、数据类型

#### 八种基本类型

|         | 字节数(bit) | 取值范围             | 默认值   |
| ------- | ----------- | -------------------- | -------- |
| byte    | 1           | -2^7~2^7-1           | 0        |
| boolean | 1           | 只有false和true      | false    |
| short   | 2           | -2^15~2^15-1         | 0        |
| char    | 2           | -2^15~2^15-1         | "\u0000" |
| int     | 4           | -2^31~2^31-1         | 0        |
| float   | 4           | -3.403E38~3.408E38   | 0f       |
| double  | 8           | -1.798E308~1.798E308 | 0.0      |
| long    | 8           | -2^63~2^63-1         | 0L       |

#### String

String内部被标记为final

   ```
   @Stable
   private final byte[] value;
   ```

##### 不可变的好处：

- String的hash值经常被使用，String不可变使得hash值也不变，只需要进行一次hash运算
- String经常作为参数，String不可变使其参数更安全
- String Pool引用更快

##### StringBuilder与StringBuffer

StringBuilder速度快 ，StringBuffer线程安全

### 二、面向对象

#### 继承

- 子类拥有父类非 private 的属性、方法。
- 子类可以拥有自己的属性和方法，即子类可以对父类进行扩展。
- 子类可以用自己的方式实现父类的方法。
- Java 的继承是单继承，但是可以多重继承，单继承就是一个子类只能继承一个父类，多重继承就是，例如 B 类继承 A 类，C 类继承 B 类，所以按照关系就是 B 类是 C 类的父类，A 类是 B 类的父类，这是 Java 继承区别于
  C++ 继承的一个特性。
- 提高了类之间的耦合性（继承的缺点，耦合度高就会造成代码之间的联系越紧密，代码独立性越差）。

#### 初始化顺序

1. 父类（静态变量、静态语句块）
2. 子类（静态变量、静态语句块）
3. 父类（实例变量、普通语句块）
4. 父类（构造函数）
5. 子类（实例变量、普通语句块）
6. 子类（构造函数）

### 三、java容器

#### ArrayList和LinkedList

大多数情况下使用的都是ArrayList，因为ArrayList支持随机访问，在get（index）的时候ArrayList更快

#### ArrayList扩容

ArrayList的扩容机制比较简单，当元素不够用的时候调用grow()方法进行扩容，扩大到原来长度的1.5倍左右。

```java
/**
 * 要分配的最大数组大小
 */
private static final int MAX_ARRAY_SIZE=Integer.MAX_VALUE-8;

/**
 * ArrayList扩容的核心方法。
 */
private void grow(int minCapacity){
        // oldCapacity为旧容量，newCapacity为新容量
        int oldCapacity=elementData.length;
        //将oldCapacity 右移一位，其效果相当于oldCapacity /2，
        //我们知道位运算的速度远远快于整除运算，整句运算式的结果就是将新容量更新为旧容量的1.5倍，
        int newCapacity=oldCapacity+(oldCapacity>>1);
        //然后检查新容量是否大于最小需要容量，若还是小于最小需要容量，那么就把最小需要容量当作数组的新容量，
        if(newCapacity-minCapacity< 0)
        newCapacity=minCapacity;
        // 如果新容量大于 MAX_ARRAY_SIZE,进入(执行) `hugeCapacity()` 方法来比较 minCapacity 和 MAX_ARRAY_SIZE，
        //如果minCapacity大于最大容量，则新容量则为`Integer.MAX_VALUE`，否则，新容量大小则为 MAX_ARRAY_SIZE 即为 `Integer.MAX_VALUE - 8`。
        if(newCapacity-MAX_ARRAY_SIZE>0)
        newCapacity=hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData=Arrays.copyOf(elementData,newCapacity);
        }
```

值得一提的是，ArrayList有一个ensureCapacity方法可以在添加添加元素时手动扩容以减少增量扩容的次数。

#### HashMap

HashMap主要用来存放键值对，它基于hash表的Map接口实现，是常用的Map集合之一，是非线程安全的。在java8之前HashMap是由数组+链表组成的，java8改为了数组+链表+红黑树。

##### 几个重要变量

- loadFactory：加载因子，控制数组存放数据的疏密程度，默认0.75f。loadfactory越接近1，链表越长，存放的数据越多，查找效率越低。越接近0则反之。
- threshold：threshold(12) = capacity(16) * loadFactory(0.75f)

##### HashMap扩容

resize()方法进行扩容，每次扩容会伴随一次重新hash分配，并且会遍历hashMap中的所有元素。所以要尽量降低扩容次数。

##### HashMap树化

HashMap只有在数组长度>64以及链表长度>8的时候才会进行树化，否则优先扩容。

### 四、反射

#### 什么是反射机制

对于任意一个类，在运行状态中，动态获取它的所有属性和方法，就叫反射。

#### 反射的优点和缺点

- 优点：运行期类型的判断，动态加载类，提高代码灵活度
- 缺点：反射相当于一系列解释性操作，性能比直接的java代码慢

#### 举几个反射用到的例子

1. JDBC中的class.forName()
2. Spring框架中的yml配置
3. 一些校验工具
4. 动态代理

#### 获取反射的三种方法

1. 通过对象实例的getClass()
2. 通过类路径class.forName(类路径)
3. 通过类名.class

### 五、动态代理

#### 代理模式

代理模式是一种设计模式，能够使得在不修改源目标的前提下，额外扩展源目标的功能。

#### 动态代理

通过反射机制，在运行时动态生成需要所需代理的class。

##### 优点

避免了静态代理大量的冗余代码

##### JDK动态代理

JDK动态代理依赖两个核心类，reflect和invocationHandler。

核心方法：newProxyInstance()，产生代理对象

```java
public static Object newProxyInstance(ClassLoader loader,//类加载器，用于加载代理对象
        Class<?>[]interfaces,//被代理类实现的一个接口
        InvocationHandler h//实现了InvocationHandler接口的对象
        )
        throws IllegalArgumentException
        {
        ......
        }
```

要实现自定义逻辑，还需要实现InvocationHandler中的invoke方法

```java
public interface InvocationHandler {

    /**
     * 当你使用代理对象调用方法的时候实际会调用到这个方法
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
```

参数说明：

- proxy：动态生成的代理类
- method：与代理类对象调用的方法相对应
- args：当前method方法的参数

###### JDK动态代理的步骤

1. 被代理类必须实现一个接口，接口中的方法就是我们进行功能扩展的方法；
2. 定义一个JDK动态代理类实现InvocationHandler接口中invoke（）方法；
3. 利用Proxy.newProxyInstance得到代理类；
4. 调用代理类中被扩展的方法；

JDK代理有一个致命弊端是只能代理实现了接口的类，为了解决这个问题，我们需要CGLIB动态代理

##### CGLIB动态代理

步骤

1. 自定义MethodInterceptor，实现其中的interceptor方法，对目标类进行增强
2. 使用Enhancer获得代理类

##### 两者比较

CGLIB可以代理任何类，JDK动态代理只能代理实现了接口的类或直接代理接口。但是从效率和安全性来看，还是JDK动态代理更好，在能使用JDK动态代理的情况下应尽可能使用JDK动态代理。

### 六、IO流

#### 有哪些常见的IO模型

UNINX操作系统下，一共有5种IO模型：同步阻塞I/O，同步非阻塞I/O，I/O多路复用，信号驱动I/O，异步I/O

根据大学里学到的操作系统相关的知识：为了保证操作系统的稳定性和安全性，一个进程的地址空间划分为 **用户空间（User space）** 和 **内核空间（Kernel space ）**
，我们平常运行的应用程序都是在用户空间，内核空间可以操作系统态级别的资源。

#### JAVA中的三种常见IO模型

#### BIO（BlockingIO）

BIO属于同步阻塞I/O。一个请求对应一个线程。

应用程序发起read调用后，会一直阻塞，直到内核把数据拷贝到用户空间。

在客户端连接数量不高的情况下，是没问题的，但在面对十万甚至百万级连接的时候，传统的BIO模型是无能为力的。这时候就需要更高效的I/O模型来应对更高的并发量。

#### NIO（Non Blocking IO）

NIO通过selector（select、poll、epoll）实现了一个线程对应多个请求，selector会进行轮询，有IO请求就处理。

#### AIO （Asynchronous IO）

AIO是在java7中引入的，它引入了异步通道的概念，先由操作系统完成后通知服务端启动线程去处理，代替原来的轮询操作

![img](https://img-blog.csdnimg.cn/20200921144020401.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2l0d3htaW5n,size_16,color_FFFFFF,t_70)
