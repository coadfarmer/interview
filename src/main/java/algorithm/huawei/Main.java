package algorithm.huawei;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class Main{
        public static void main(String[] args) throws IOException {
            try {
                System.out.println("111");
                new Thread(() -> System.out.println(2/0)).start();
            } catch (Exception e) {
                System.out.println("222");
            }
        }

    public static String reverse(String s){
//        char[] chars = s.toCharArray();
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < chars.length; i++) {
//            result.append(chars[chars.length - i - 1]);
//        }
//        return result.toString();
        return StringUtils.reverse(s);
    }

//    private static final Main main = new Main();
//
//    private  Main(){
//
//    }
//
//    public Main getMain(){
//        return main;
//    }

}
