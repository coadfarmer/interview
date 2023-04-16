package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在这个示例中，我们创建了一个名为ReentrantLockDemo的类，其中包含了一个可重入锁实例lock和一个计数器count。increment()和getCount()方法都使用了可重入锁，以保证线程安全。在increment()方法中，我们首先获取锁，然后执行count++操作，最后释放锁。在getCount()方法中，我们同样获取锁，然后返回计数器的值，最后释放锁。
 *
 * 我们使用两个线程t1和t2来并发地调用increment()方法，然后通过getCount()方法获取计数器的值，最后输出结果。
 *
 * 值得注意的是，当使用ReentrantLock时，必须手动释放锁，否则会导致死锁或长时间的阻塞。我们可以使用try-finally代码块来确保锁被正确地释放。另外，使用可重入锁时要注意避免死锁和饥饿等问题
 */
public class ReentrantLockDemo {
    private final Lock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                demo.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                demo.increment();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.getCount());
    }
}
