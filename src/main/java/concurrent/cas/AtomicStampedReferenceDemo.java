package main.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: xjjiang
 * @Data: 2022/5/18 10:01
 * @Description: AtomicStampedReference的特点是利用版本号机制解决了ABA问题
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        // initialRef：初始值；initialStamp：初始版本号
        final int initialRef = 0, initialStamp = 0;
        final AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(initialRef, initialStamp);
        System.out.println("currentValue=" + asr.getReference() + ", currentStamp=" + asr.getStamp());

        // newReference：更新后的值；newStamp：新版本号
        final int newReference = 666, newStamp = 999;
        final boolean casResult = asr.compareAndSet(initialRef, newReference, initialStamp, newStamp);
        System.out.println("currentValue=" + asr.getReference()
                + ", currentStamp=" + asr.getStamp()
                + ", casResult=" + casResult);

        // 获取当前的值和当前的 stamp 值
        int[] arr = new int[1];
        final Integer currentValue = asr.get(arr);
        final int currentStamp = arr[0];
        System.out.println("currentValue=" + currentValue + ", currentStamp=" + currentStamp);

        // 单独设置 stamp 值
        final boolean attemptStampResult = asr.attemptStamp(newReference, 88);
        System.out.println("currentValue=" + asr.getReference()
                + ", currentStamp=" + asr.getStamp()
                + ", attemptStampResult=" + attemptStampResult);

        // 获取更新后的值和版本号
        asr.set(asr.getReference(), asr.getStamp());
        System.out.println("currentValue=" + asr.getReference() + ", currentStamp=" + asr.getStamp());

        //第二次更新
        final int newReference2 = 777, newStamp2 = 888;
        boolean casResult2 = asr.compareAndSet(asr.getReference(), newReference2, asr.getStamp(), newStamp2);
        System.out.println("currentValue=" + asr.getReference()
                + ", currentStamp2=" + asr.getStamp()
                + ", casResult2=" + casResult2);


    }
}
