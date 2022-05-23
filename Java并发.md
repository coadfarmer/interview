### 为什么要使用多线程

提高系统的整体并发能力和性能。

从计算机底层的角度来说，在单核时代多线程通过上下文切换以提高系统的整体利用率，多核时代减少了上下文切换的开销，进一步提高了资源利用率。

### 线程的6种状态

| 状态名称     | 中文名   | 解释                                    |
| ------------ | :------- | --------------------------------------- |
| NEW          | 初始状态 | new一个实例                             |
| RUNNABLE     | 可运行   | 调用start（）方法                       |
| BLOCKED      | 阻塞     | Synchronized等锁                        |
| WAITING      | 等待     | Object.wait()，只有通过notify（）能唤醒 |
| TIME_WAITING | 超时     | Thread.sleep(time)                      |
| TERMINATED   | 终止     | run方法执行完                           |

### Java对象头与锁标志位

![img](https://img2020.cnblogs.com/blog/443934/202012/443934-20201207134826598-1740849743.png)

对象头MarkWord中有两位是锁标识位，有1位是偏向锁标识为，通过标识位的改变升级锁

### 什么叫自旋锁

自旋锁是指当一个线程在试图获取锁的时候，如果锁已经被其他线程获取，那么该线程将循环等待，直到获取到它需要的锁。

通过参数**-XX:PreBlockSpin**设置自旋次数

### Synchronized

在Java早期版本，Synchronized锁属于重量级锁，Java1.6对Synchronized锁进行了大量优化，主要是使Synchronized锁随着竞争激烈从偏向锁，轻量级锁，重量级锁不断升级，锁升级之后不能降级。

| 锁       | 优点                                                         | 缺点                                         | 适用场景                                               |
| -------- | ------------------------------------------------------------ | -------------------------------------------- | ------------------------------------------------------ |
| 偏向锁   | 加锁和解锁不需要额外的消耗，与执行非同步方法仅存在纳秒级的差距 | 如果线程间存在竞争，会带来额外的锁撤销的消耗 | 适用于只有一个线程访问同步块的情况                     |
| 轻量级锁 | 竞争的线程不会堵塞，提高了程序的响应速度                     | 始终得不到锁的线程，使用自旋会消耗CPU        | 追求响应时间，同步块执行速度非常块，只有两个线程竞争锁 |
| 重量级锁 | 线程竞争不使用自旋，不会消耗CPU                              | 线程堵塞，响应时间缓慢                       | 追求吞吐量，同步块执行速度比较慢，竞争锁的线程大于2个  |

> 详见《[不可不说的 Java“锁”事](https://tech.meituan.com/2018/11/15/java-lock.html)》

### synchronized 和 ReentrantLock 的区别

1. 两者都是可重入锁
  - 可重入锁：也叫递归锁。是指在一个线程中，可以多次获取同一把锁。
2. synchronized依赖于JVM而ReentrantLock依赖于AQS
3. ReentrantLock比Synchronized增加了一些高级功能
  - 等待可中断
  - 可实现公平锁

### volatile

- 保证被volatile修饰的变量存取都在主存中进行

- 原子操作，防止指令重排


### ThreadLocal

ThreadLocal实际上是在每一个线程中存储的一个变量副本。

```java
public class Thread implements Runnable {
    ThreadLocal.ThreadLocalMap threadLocals = null;
}
```

```java
public class ThreadLocal<T> {
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            map.set(this, value);
        } else {
            createMap(t, value);
        }
    }
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
}
```

### 读写锁

- 当写锁没有被线程持有时，多个线程能共同持有读锁
- 当写锁被线程持有时，读线程获取读锁的操作会被阻塞，其他线程获取写锁的操作也会被阻塞

读写锁在读多写少的场景能发挥优势。

### 可重入锁

### 乐观锁与悲观锁

- 悲观锁

  总是假设最坏的状态，每次拿数据的时候认为别人都会修改，所以在每次拿数据的时候都会上锁。

- 乐观锁

  总是假设最好的状态，每次拿数据的时候都认为别人不会修改，所以不会上锁。但是在更新的时候会判断一下别人有没有更新这个数据。乐观锁可以使用CAS和版本号实现。

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

  - AtomicInteger
  - AtomicLong
  - AtomicBoolean

  > 相比于AtomicLong，在Java8以上推荐使用LongAdder，在高竞争下，这个的吞吐量更高

- 数组类型

  - AtomicIntegerArray
  - AtomicLongArray
  - AtomicReferenceArray

- 引用类型

  - AtomicReference
  - AtomicStampedReference ：带有版本号的引用类型原子类，可以解决ABA问题
  - AtomicMarkableReference：带有标记的引用类型原子类

- 对象的属性修改类型

  - AtomicIntegerFieldUpdater
  - AtomicLongFieldUpdater
  - AtomicReferenceFieldUpdater

使用CAS+volatile和native方法实现原子操作，避免了synchronized的高开销。

### AQS简述

1. AQS（AbstractQueuedSynchronizer）是一个用来构建锁和同步器的框架
2. AQS
   核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待被唤醒时锁分配机制，AQS是用CLH队列锁实现这个分配机制的，就是将暂时获取不到锁的线程加入到队列中。
3. AQS通过一个volatile的int类型的成员变量state来表示同步状态，通过内置的队列来完成资源获取的排队工作，通过CAS完成对state值的修改

### AQS重要方法

| 方法名                                      | 描述                                                         |
| :------------------------------------------ | :----------------------------------------------------------- |
| protected boolean isHeldExclusively()       | 该线程是否正在独占资源。只有用到 Condition 才需要去实现它。  |
| protected boolean tryAcquire(int arg)       | 独占方式。arg 为获取锁的次数，尝试获取资源，成功则返回 True，失败则返回 False。 |
| protected boolean tryRelease(int arg)       | 独占方式。arg 为释放锁的次数，尝试释放资源，成功则返回 True，失败则返回 False。 |
| protected int tryAcquireShared(int arg)     | 共享方式。arg 为获取锁的次数，尝试获取资源。负数表示失败；0 表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。 |
| protected boolean tryReleaseShared(int arg) | 共享方式。arg 为释放锁的次数，尝试释放资源，如果释放后允许唤醒后续等待结点返回 True，否则返回 False。 |

### 公平锁与非公平锁

- **公平锁** ：按照线程在队列中的排队顺序，先到者先拿到锁
- **非公平锁** ：当线程要获取锁时，先通过两次 CAS 操作去抢锁，如果没抢到，当前线程再加入到队列中等待唤醒。

### AQS组件

- Semaphore(信号量)：可以指定多个线程访问同一个资源
- CountDownLatch（倒计时）：用来控制线程等待，它可以让某一个线程等待到设定的临界值或倒计时结束再执行
- CyclicBarrier(循环栅栏)：和CountDownLatch类似，它也可以实现线程等待，但它的功能比CountDownLatch更复杂和强大

详见

《[不可不说的 Java“锁”事](https://tech.meituan.com/2018/11/15/java-lock.html)》

### ReentrantLock

可重入锁，基于AQS实现的

lock（）调用tryAcquire尝试获取锁

unlock（）调用tryRelease释放锁

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

### Executor框架

Executor 框架是 Java5 之后引进的，在 Java 5 之后，通过 Executor 来启动线程比使用 Thread 的 start
方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免this 逃逸问题。

> this逃逸问题：在构造方法调用之前就持有了这个对象的引用，调用尚未构造完全的对象的方法显然是不合理的

### Executors

Executors类中几种常见的线程池：

- FixedThreadPool：固定线程数的线程池，只有一个参数确定核心线程数和最大线程数
- SingleThreadExecutor ：只有一个线程的线程池
- CachedThreadPool ：一个会根据需要创建新线程的线程池
- ScheduledThreadPoolExecutor ：一个定期执行任务的线程池

阿里巴巴开发手册中强制不允许使用Executors

> 1）FixedThreadPool 和 SingleThreadPool: 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
>
>  2）CachedThreadPool 和 ScheduledThreadPool: 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM

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

  - **`ThreadPoolExecutor.AbortPolicy`** ：抛出 `RejectedExecutionException`来拒绝新任务的处理。（默认）

  - **`ThreadPoolExecutor.CallerRunsPolicy`** ：调用执行自己的线程运行任务，也就是直接在调用`execute`方法的线程中运行(`run`)
    被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。因此这种策略会降低对于新任务提交速度，影响程序的整体性能。如果您的应用程序可以承受此延迟并且你要求任何一个任务请求都要被执行的话，你可以选择这个策略。
  - **`ThreadPoolExecutor.DiscardPolicy`** ：不处理新任务，直接丢弃掉。
  - **`ThreadPoolExecutor.DiscardOldestPolicy`** ： 此策略将丢弃最早的未处理的任务请求

