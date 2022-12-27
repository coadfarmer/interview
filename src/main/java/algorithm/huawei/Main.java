package algorithm.huawei;

import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] in = bf.readLine().split(";");
        int x = 0;
        int y = 0;
        for(String s : in){
            // 不满足题目给定坐标规则
            if(!s.matches("[WASD][0-9]{1,2}")){
                continue;
            }
            int val = Integer.valueOf(s.substring(1));
            switch(s.charAt(0)){
                case 'W':
                    y += val;
                    break;
                case 'S':
                    y -= val;
                    break;
                case 'A':
                    x -= val;
                    break;
                case 'D':
                    x += val;
                    break;
            }
        }
        System.out.println(x+","+y);
    }
}
