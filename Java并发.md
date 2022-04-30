### 为什么要使用多线程

提高系统的整体并发能力和性能。

从计算机底层的角度来说，在单核时代多线程通过上下文切换以提高系统的整体利用率，多核时代减少了上下文切换的开销，进一步提高了效率。

### 线程的6种状态

| 状态名称    | 状态说明 |
| ----------- | :------- |
| NEW         | 初始状态 |
| RUNNABLE    | 运行中   |
| BLOCKED     | 阻塞     |
| WATING      | 等待     |
| TIME_WATING | 超时     |
| TERMINATED  | 终止     |

### Java对象头与锁标志位

![image-20220428153612127](C:\Users\38293\AppData\Roaming\Typora\typora-user-images\image-20220428153612127.png)

对象头MarkWord中有两位是锁标识位，有1位是偏向锁标识为，通过标识位的改变升级锁

### 什么叫自旋锁

自旋锁是指当一个线程在试图获取锁的时候，如果锁已经被其他线程获取，那么该线程将循环等待，直到获取到它需要的锁。

### Synchronized

在Java早期版本，Synchronized锁属于重量级锁，Java1.6对Synchronized锁进行了大量优化，主要是使Synchronized锁随着竞争激烈从偏向锁，轻量级锁，重量级锁不断升级，锁升级之后不能降级。

| 锁       | 优点                                                         | 缺点                                         | 适用场景                                               |
| -------- | ------------------------------------------------------------ | -------------------------------------------- | ------------------------------------------------------ |
| 偏向锁   | 加锁和解锁不需要额外的消耗，与执行非同步方法仅存在纳秒级的差距 | 如果线程间存在竞争，会带来额外的锁撤销的消耗 | 适用于只有一个线程访问同步块的情况                     |
| 轻量级锁 | 竞争的线程不会堵塞，提高了程序的响应速度                     | 始终得不到锁的线程，使用自旋会消耗CPU        | 追求响应时间，同步块执行速度非常块，只有两个线程竞争锁 |
| 重量级锁 | 线程竞争不使用自旋，不会消耗CPU                              | 线程堵塞，响应时间缓慢                       | 追求吞吐量，同步块执行速度比较慢，竞争锁的线程大于2个  |

> 详见https://segmentfault.com/a/1190000038403889#:~:text=%E5%81%8F%E5%90%91%E9%94%81%E6%98%AFHotSpot%20%E8%99%9A%E6%8B%9F,%E6%93%8D%E4%BD%9C%E6%AD%A5%E9%AA%A4%E9%9C%80%E8%A6%81%E5%8E%9F%E5%AD%90%E6%8C%87%E4%BB%A4%E3%80%82

### volatile

原子操作，防止指令重排

### ThreadLocal

ThreadLocal实际上是在每一个线程中都存一个副本，保证每个线程的变量独立

### 乐观锁与悲观锁

- 悲观锁

  总是假设最坏的状态，每次拿数据的时候认为别人都会修改，所以在每次拿数据的时候都会上锁。

- 乐观锁

  总是假设最好的状态，每次拿数据的时候都认为别人不会修改，所以不会上锁。但是在更新的时候会判断一下别人有没有更新这个数据，可以使用CAS实现。

### 什么是CAS

CAS: compare and swap（比较与交换），是一种有名的无锁算法。它可以不使用锁来实现线程间的变量同步，也就是非阻塞同步。CAS算法涉及到三个操作数：

- 需要读写的内存值V
- 需要比较的值A
- 拟写入的新值B

当且仅当V的值等于A时，CAS用原子的方式将V更新为B。一般情况下是一个自旋操作。

#### 如何解决ABA问题

增加版本号

### Atomic

原子类，主要包括一下四大类

- 基本类型
- 数组类型
- 引用类型
- 对象的属性修改类型

使用CAS+volatile和native方法实现原子操作，避免了synchronized的高开销。

### AQS简述

1. AQS是一个用来构建锁和同步器的框架
2. AQS 核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待被唤醒时锁分配机制，这个机制 就是AQS
   是用CLH队列锁，就是将暂时获取不到锁的线程加入到队列中。
3. AQS通过一个Volatile的int类型的成员变量state来表示同步状态，通过内置的FIFO队列来完成资源获取的排队工作，通过CAS完成对state值的修改

### AQS组件

- Semaphore(信号量)：可以指定多个线程访问同一个资源
- CountDownLatch（倒计时）：用来控制线程等待，它可以让某一个线程等待到倒计时结束再执行
- CyclicBarrier(循环栅栏)：和CountDownLatch类似，它也可以实现线程等待，但它的功能比CountDownLatch更复杂和强大

AQS框架

![img](https://p1.meituan.net/travelcube/82077ccf14127a87b77cefd1ccf562d3253591.png)

### ReentrantLock

可重入锁，基于AQS实现的

tryAcquire，tryRelease

### 线程通信的几种方式

详见https://redspider.gitbook.io/concurrent/di-yi-pian-ji-chu-pian/5

1. 锁与同步
2. 等待/通知机制
3. 信号量
4. 管道：JDK提供了`PipedWriter`、 `PipedReader`、 `PipedOutputStream`、 `PipedInputStream`。其中，前面两个是基于字符的，后面两个是基于字节流的。
5. 其他：ThreadLocal等

### CompletableFuture

一个在Java8被引入的用于异步编程的类

- get()：获取异步计算的结果

  > 注意：get（）方法并不会阻塞，因为我们已经知道异步计算的结果了

- allOf()：并行运行多个（互相不相关的）CompletableFuture

- complete()：手动完成任务

- runAsync()：`runAsync()` 方法接受的参数是 `Runnable` ，这是一个函数式接口，不允许返回值。当你需要异步操作且不关心返回结果的时候可以使用 `runAsync()` 方法。

- SupplyAsync()：`supplyAsync()` 方法接受的参数是 `Supplier<U>` ，这也是一个函数式接口，`U` 是返回结果值的类型。当你需要异步操作且关心返回结果的时候,可以使用 `supplyAsync()`
  方法。

- thenCompose()：可以使用 `thenCompose()` 按顺序链接两个 `CompletableFuture` 对象。

  ```java
  CompletableFuture<String> future
          = CompletableFuture.supplyAsync(() -> "hello!")
          .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world!"));
  assertEquals("hello!world!", future.get());
  ```

- thenCombine():`thenCombine()` 会在两个任务都执行完成后，把两个任务的结果合并。两个任务是并行执行的，它们之间并没有先后依赖顺序

  ```java
  CompletableFuture<String> completableFuture
          = CompletableFuture.supplyAsync(() -> "hello!")
          .thenCombine(CompletableFuture.supplyAsync(
                  () -> "world!"), (s1, s2) -> s1 + s2)
          .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "nice!"));
  assertEquals("hello!world!nice!", completableFuture.get());
  ```

## 线程池

### 线程池的好处

- **降低资源消耗**。通过重复利用已创建的线程降低线程创建和销毁带来的消耗。
- **提高响应速度**。当任务到达时，任务可以不需要等待线程创建就能立即执行。
- **提高线程的可管理性**。使用线程池可以进行统一的分配，调优和监控。

### Excutor框架

Executor 框架是 Java5 之后引进的，在 Java 5 之后，通过 Executor 来启动线程比使用 Thread 的 start 方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免
this 逃逸问题。

### **ThreadPoolExecutor**

```java
/**
 * 用给定的初始参数创建一个新的ThreadPoolExecutor。
 */
public ThreadPoolExecutor(int corePoolSize,//线程池的核心线程数量
                          int maximumPoolSize,//线程池的最大线程数
                          long keepAliveTime,//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                          TimeUnit unit,//时间单位
                          BlockingQueue<Runnable> workQueue,//任务队列，用来储存等待执行任务的队列
                          ThreadFactory threadFactory,//线程工厂，用来创建线程，一般默认即可
                          RejectedExecutionHandler handler//拒绝策略，当提交的任务过多而不能及时处理时，我们可以定制策略来处理任务
                           ) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

#### **三个最重要的参数**

- corePoolSize：核心线程数定义了最小可以同时运行的线程数量
- maximunPoolSize：当队列的任务达到队列容量时，当前可以同时运行的线程数变为最大线程数
- workQueue：当新任务来的时候会先判断是否达到核心线程数，如果已达到就先放入workQueue中

#### 其他常见参数

- keepAliveTime：当线程数大于核心线程数时，多余的空闲线程存活的最长时间
- unit：keepAliceTime参数的时间单位
- threadFactory：线程工厂，用来创建线程
- handler：饱和策略

