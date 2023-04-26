import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xjjiang
 * @Data: 2023/4/22 16:03
 * @Description:
 */
public class RandomTest {

    /**
     * 给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
     * 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现。
     * 示例 1：
     * 输入：s = "hello"
     * 输出："holle"
     * 示例 2：
     * 输入：s = "leetcode"
     * 输出："leotcede"
     * 提示：
     * 1 <= s.length <= 3 * 105
     * s 由 可打印的 ASCII 字符组成
     */
    @Test
    public void test(){
        String s = "huawei" ;
        String result = reverseString(s);
        System.out.println(result);
    }

    public String reverseString(String s){
        char[] chars = s.toCharArray();
        char head;
        char tail;
        int i = 0,j = chars.length-1;
        while (i<j && i<chars.length){
            while (!isVowel(chars[i]) && i<j){
                i++;
            }
            head = chars[i];
            while (!isVowel(chars[j]) && i<j){
                j--;
            }
            tail = chars[j];
            if(i < j){
                if(isVowel(chars[i]) && isVowel(chars[j])){
                    chars[i] = tail;
                    chars[j] = head;
                }
                i++;
                j--;
            }
        }
        return new String(chars);
    }


    public boolean isVowel(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ;
    }

}
