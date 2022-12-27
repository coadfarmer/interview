package algorithm.huawei;

import java.util.Scanner;

/**
 * @Author: xjjiang
 * @Data: 2022/12/26 22:25
 * @Description: 密码验证
 */
public class HJ20 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        if (password.length() <= 8) {
            System.out.println("NG"+"密码长度需大于8");
            return;
        }
        //1.没有重复子串
        if(getString(password,0,3)){
            System.out.println("NG"+"有重复字符串");
            return;
        }
        //2.密码类型大于三种（大写、小写、数字，符号）
        if(!checkTypeNumbers(password)){
            System.out.println("NG"+"密码类型大于3");
            return;
        }
        System.out.println("OK");

    }

    //检查重复子串
    private static boolean getString(String str, int l, int r) {
        if (r >= str.length()) {
            return false;
        }
        if (str.substring(r).contains(str.substring(l, r))) {
            return true;
        } else {
            return getString(str,l+1,r+1);
        }
    }

    //检查密码类型大于三种（大写、小写、数字，符号）
    public static boolean checkTypeNumbers(String str){
        int count = 0;
        if(str.matches("[a-z]")){
            count++;
        }
        if(str.matches("[A-Z]")){
            count++;
        }
        if(str.matches("[0-9]")){
            count++;
        }
        if(str.matches("[^a-zA-Z0-9]")){
            count++;
        }
        if(count>=3){
            return true;
        }else {
            return false;
        }
    }

}
