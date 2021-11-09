### synchronized

 锁

### volatile

原子操作

### ThreadLocal

ThreadLocal实际上是在每一个线程中都存一个副本，保证每个线程的变量独立

### Atomic

### AQS

1. 一个用来构建锁和同步器的框架
2. AQS 核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制 AQS 是用
   CLH队列锁实现的，即将暂时获取不到锁的线程加入到队列中。

### ReentrantLock

可重入锁，基于AQS实现的

### java线程的六种状态

1. new：初始化状态
2. runnable：运行状态，有可能真的在运行，有可能在等待cpu
3. block：阻塞状态
4. wating：等待
5. timed_wating：限期等待
6. terminated：结束

### 线程通信的几种方式

详见https://redspider.gitbook.io/concurrent/di-yi-pian-ji-chu-pian/5

1. 锁与同步
2. 等待/通知机制
3. 信号量
4. 管道：JDK提供了`PipedWriter`、 `PipedReader`、 `PipedOutputStream`、 `PipedInputStream`。其中，前面两个是基于字符的，后面两个是基于字节流的。
5. 其他：ThreadLocal等

