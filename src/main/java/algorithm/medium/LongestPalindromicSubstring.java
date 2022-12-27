package algorithm.medium;

/**
 * @Author: xjjiang
 * @Data: 2022/12/27 15:31
 * @Description: 最长回文子串
 */
public class LongestPalindromicSubstring {


    public static void main(String[] args) {
        String r = longestPalindrome("abccbxbe");
//        String r = "abccbxbe".substring(7, 8);
        System.out.println(r);
    }


    /**
     * 动态规划算法
     * 主要思想：在一个回文串两端添加相同的元素，那么这个字符串仍热是个回文串
     * 步骤：设定两个下标，从左往右扫描。一旦i，j重合，那么i下标移动到头部，从头开始扫描，j下标向后移动一个元素
     * 当i下标和j下标重合的时候，判断出去这两个元素中间的元素是否是回文串，如果是，那么最长回文串就是当前形成的新串
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        int j = 0;
        String result = "";
        while (j < chars.length) {
            if (j == 0) {
                j++;
            } else if (chars[i] != chars[j]) {
                i++;
            } else if (i == j) {
                i = 0;
                j++;
            } else if (chars[i] == chars[j]) {
                if(j-1>1) {
                    String currentString = s.substring(i, j + 1); //当前子串
                    String midString = s.substring(i + 1, j);
                    if (isPalindromicSubstring(midString)) {
                        result = currentString;
                    }
                }else {
                    result = s.substring(i,j+1);
                }
                i = 0;
                j++;
            }
        }
        return result;
    }

    /**
     * 判断是否是回文子串
     *
     */
    public static boolean isPalindromicSubstring(String s){
        char[] charArray = s.toCharArray();
        int i = 0, j = charArray.length-1;
        while (i < j){
            if(charArray[i] != charArray[j]){
                return false;
            }
            i++;j--;
        }
        return true;
    }
}
