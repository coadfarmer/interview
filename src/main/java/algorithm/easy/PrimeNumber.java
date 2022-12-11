package algorithm.easy;

import org.junit.Test;

/**
 * @Author: xjjiang
 * @Data: 2022/12/11 6:50
 * @Description: 寻找100以内的素数
 */
public class PrimeNumber {
    @Test
    public void test(){
        int count = 0;
        long nanoTime1 = System.nanoTime();
        for (int i = 1; i < 10000; i++) {
            if(isPrimeNumber(i)){
//                System.out.println(i);
                count++;
            }
        }
        System.out.println("耗时：" + (System.nanoTime()-nanoTime1));
        System.out.println("count:"+count);
    }

    /**
     * 判断是否为素数
     */
    public boolean isPrimeNumber(int number){
        for (int i = 2; i < number; i++) {
            if(number%i==0){
                return false;
            }
        }
        return true;
    }
}
