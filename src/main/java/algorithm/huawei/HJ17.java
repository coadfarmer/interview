package algorithm.huawei;

import java.util.Scanner;

/**
 * @Author: xjjiang
 * @Data: 2022/12/26 21:09
 * @Description: 坐标移动
 */
public class HJ17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] split = s.split(";");
        int m=0,n=0;
        for (String s1 : split) {
            if(s1.matches("[WASD][0-9]{1,2}")){
                char[] chars = s1.toCharArray();
                char aChar = chars[0];
                int number = Integer.valueOf(s1.substring(1));
                if(aChar == 'A'){
                    m = m-number;
                }else if(aChar == 'S'){
                    n = n-number;
                }else if(aChar == 'W'){
                    n = n+number;
                }else if(aChar == 'D'){
                    m = m+number;
                }
            }
        }
        System.out.println("("+m+","+n+")");
    }

}
