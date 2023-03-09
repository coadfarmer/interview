package algorithm.huawei;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        String s = "Hello world";
        String[] split = s.split(" ");

        StringBuilder result = new StringBuilder();
        for (String s1 : split) {
            String reverse = reverse(s1);
            result.append(reverse).append(" ");
        }
        System.out.println(result);
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
