package algorithm.huawei;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: xjjiang
 * @Data: 2022/12/27 10:07
 * @Description:
 */
public class HJ23 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        char[] chars = s.toCharArray();
        Map<Character,Integer> charMap = new HashMap<>();
        for (char aChar : chars) {
            charMap.put(aChar,charMap.getOrDefault(aChar,0)+1);
        }
        Integer minChar = charMap.values().stream().min(Comparator.comparingInt(o -> o)).get();
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            if(Objects.equals(minChar,entry.getValue())){
                String rep = String.valueOf(entry.getKey());
                s = s.replace(rep,"");
            }
        }
        System.out.println(s);
    }

}
