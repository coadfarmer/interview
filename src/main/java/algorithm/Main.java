package algorithm;

import java.util.*;

/**
 * @Author: xjjiang
 * @Data: 2022/12/28 16:04
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        int i = scanner.nextInt();
        int[] n1 = new int[i];
        int[] n2 = new int[i];
        for (int j = 0; j < i; j++) {
            n1[j] = scanner.nextInt();
            n2[j] = scanner.nextInt();
        }
        //存储某个数是否被查找过
        Map<String,Integer> taskMap = new HashMap<>();
        for (int v = 0; v < t; v++) {
            findDepend(v+"",v,n1,n2,taskMap);
        }
        Integer result = taskMap.values().stream().max(Comparator.comparingInt(o -> o)).get();
        System.out.println(result);

    }

    public static int findDepend(String s,int m, int[] n1,int[] n2,Map<String,Integer> taskMap){
        for (int i = 0; i < n2.length; i++) {
            if(n2[i] == m){
                String ss = s;
                if(s.length()>2){
                    ss = s.substring(0,s.length()-2);
                }
                Integer skip = taskMap.getOrDefault(ss,0);
                skip++;
                s = s + "-" + n1[i];
                taskMap.put(s,skip);
                findDepend(s,n1[i],n1,n2,taskMap);
            }
        }
        return -1;
    }

    public static int max(int[] n){
        OptionalInt max = Arrays.stream(n).max();
        if(max.isPresent()){
            return max.getAsInt();
        }
        return 0;
    }

}
