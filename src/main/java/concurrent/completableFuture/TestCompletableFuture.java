package main.java.concurrent.completableFuture;

import main.java.concurrent.cas.CASDemo;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: xjjiang
 * @Data: 2022/5/17 15:10
 * @Description: 测试completableFuture
 */
public class TestCompletableFuture {

    public static void main(String[] args) {

    }

    @Test
    public void testExceptionally() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    int i = 5 / 0;
                    return "test";
                })
                .exceptionally(throwable -> "[ERROR]: " + throwable.toString());
        //如果主线程不进入TIME_WAITING状态，可能导致异步线程中的异常捕获不到
        //Thread.sleep(1000L);
        future.complete("manual test");
        System.out.println(future.join());
    }

    /**
     * 简单Future构建
     */
    @Test
    public void testNewFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();

        new Thread(() -> future.complete("test")).start();
        String result = future.join();
        System.out.println(result);
    }

    /**
     * supplyAsync方式创建
     */
    @Test
    public void testSupplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "test");
        System.out.println(future.join());
    }

    /**
     * thenApply：thenApply提交的任务类型需遵从Function签名，也就是有入参和返回值，其中入参为前置任务的结果
     * thenAccept：thenAccept提交的任务类型需遵从Consumer签名，也就是有入参但是没有返回值，其中入参为前置任务的结果
     * thenRun：thenRun提交的任务类型需遵从Runnable签名，既没有入参也没有返回值
     */
    @Test
    public void testThenApply() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).thenApply((p) -> {
            System.out.println("compute 2");
            return p + 10;
        });
        System.out.println("result: " + future1.join());

    }

    /**
     * thenCombine
     * 同前面一组连接函数相比，thenCombine最大的不同是连接任务可以是一个独立的CompletableFuture（或者是任意实现了CompletionStage的类型），
     * 从而允许前后连接的两个任务可以并行执行（后置任务不需要等待前置任务执行完成），
     * 最后当两个任务均完成时，再将其结果同时传递给下游处理任务，从而得到最终结果。
     */
    @Test
    public void testThenCombine() {
        Integer result = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 2");
            return 10;
        }), Integer::sum).join();
        System.out.println(result);
    }

    /**
     * thenCompose
     * 前面讲了thenCombine主要用于没有前后依赖关系之间的任务进行连接。那么，如果两个任务之间有前后依赖关系，但是连接任务又是独立的CompletableFuture，该怎么实现呢？
     * 先来看一下直接使用thenApply来实现：
     */
    @Test
    public void testThenApply2() {
        CompletableFuture<CompletableFuture<Integer>> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).thenApply((r) -> CompletableFuture.supplyAsync(() -> r + 10));
        System.out.println(future.join().join());
    }

    /**
     * 可以发现，上面示例代码中，future2的类型变成了CompletableFuture嵌套，而且在获取结果的时候，也需要嵌套调用join或者get。这样，当连接的任务越多时，代码会变得越来越复杂，
     * 嵌套获取层级也越来越深。因此，需要一种方式，能将这种嵌套模式展开，使其没有那么多层级。
     * thenCompose的主要目的就是解决这个问题（这里也可以将thenCompose的作用类比于stream接口中的flatMap，因为他们都可以将类型嵌套展开）
     */
    @Test
    public void testThenCompose() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).thenCompose((r) -> CompletableFuture.supplyAsync(() -> r + 10));
        System.out.println(future.join());
    }

    /**
     * whenComplete
     * 主要用于注入任务完成时的回调通知逻辑。这个解决了传统future在任务完成时，无法主动发起通知的问题。前置任务会将计算结果或者抛出的异常作为入参传递给回调通知函数。
     */
    @Test
    public void testWhenComplete() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).whenComplete((r, e) -> {
            if (e != null) {
                System.out.println("compute failed!");
            } else {
                System.out.println("received result is " + r);
            }
        });
        System.out.println("result: " + future.join());
    }

    /**
     * handle
     * handle与whenComplete的作用有些类似，但是handle接收的处理函数有返回值，而且返回值会影响最终获取的计算结果。
     */
    @Test
    public void testHandle() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("compute 1");
            return 1;
        }).handle((r, e) -> {
            if (e != null) {
                System.out.println("compute failed!");
                return r;
            } else {
                System.out.println("received result is " + r);
                return r + 10;
            }
        });
        System.out.println("result: " + future.join());
    }

    /**
     * allof
     * 等待所有异步线程任务结束
     * 这里我们使用allof改造CASDemo中的任务
     */
    @Test
    public void testAllof() {
        CompletableFuture[] completableFutures = new CompletableFuture[20];
        for (int i = 0; i < 20; i++) {
            completableFutures[i] = CompletableFuture.supplyAsync(() -> {
                for (int j = 0; j < 1000; j++) {
                    CASDemo.increase();
                }
                return CASDemo.getCount();
            });
        }
        CompletableFuture.allOf(completableFutures).join();
        System.out.println(CASDemo.getCount());
    }


}
