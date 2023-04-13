package algorithm;

/**
 * @Author: xjjiang
 * @Data: 2023/3/27 20:17
 * @Description: 三数之和
 */
public class Test {

    /**
     * 给定1个整型数组与1个目标值N，判断数组中是否存在3个数，其和正好等于N；如果有则返回1，没有则返回0。例如：
     * 1）对于跟定的整型数组{1,2,3,5}与目标值6，则返回1；
     * 2）对于跟定的整型数组{1,2,3,5}与目标值7，则返回0。
     */
    @org.junit.Test
    public void test1(){

        int[] num = {-100,0,0,50,51};
        int n = 1;
        int print = printNum1(num, n);
        System.out.println(print);
    }

    /**
     * 三数之和
     * @param num 数组
     * @param n 目标数
     * @return 0：未找到 | 1：找到
     */
    public int printNum1(int[] num, int n) {
        for (int i = 0; i < num.length; i++) {
            for (int j = i + 1; j < num.length; j++) {
                for (int k = j + 1; k < num.length; k++) {
                    if (num[i] + num[j] + num[k] == n) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
}
