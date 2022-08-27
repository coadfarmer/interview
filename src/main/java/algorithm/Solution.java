package algorithm;

import org.junit.Test;

import java.util.Stack;

class Solution {
    public static String isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int length = s.length();
        if(length % 2 == 1) return "N";
        if(s.charAt(0) == ')' || s.charAt(0) == ']' || s.charAt(0) == '}') return "N";
        for (int i = 0; i<length; i++) {
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }else {
                if(!stack.isEmpty()) {
                    if (c != stack.pop()+getDiffer(c)) return "N";
                }else
                    return "N";
            }
        }
        return stack.isEmpty()?"N":"Y";
    }
    public static int getDiffer(char c){
        switch (c) {
            case ')' :return 1;
            case ']' :
            case '}' :
                return 2;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(isValid1("([{}()]{})"));

    }

    public static String isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c: s.toCharArray()){
            if(c=='(')stack.push(')');
            else if(c=='[')stack.push(']');
            else if(c=='{')stack.push('}');
            else if(stack.isEmpty()||c!=stack.pop())return "N";
        }
        return stack.isEmpty()?"Y":"N";
    }

}
