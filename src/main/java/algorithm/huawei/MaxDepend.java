package algorithm.huawei;

import java.util.*;

/**
 * @Author: xjjiang
 * @Data: 2022/12/29 11:26
 * @Description:
 */
public class MaxDepend {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();//最大任务数
        int y = scanner.nextInt();//依赖关系数
        Map<Integer,TreeNode> treeNodeMap = new HashMap<>();
        for (int k = 0; k < i; k++) {
            treeNodeMap.put(k,new TreeNode(k));
        }
        for (int j = 0; j < y; j++) {
            int i1 = scanner.nextInt();
            TreeNode treeNode1 = treeNodeMap.get(i1);
            int i2 = scanner.nextInt();
            List<TreeNode> children = treeNode1.treeNodes;
            TreeNode treeNode2 = treeNodeMap.get(i2);
            children.add(treeNode2);
        }

    }

    public static int max(int[] n){
        OptionalInt max = Arrays.stream(n).max();
        if(max.isPresent()){
            return max.getAsInt();
        }
        return 0;
    }

    static class TreeNode{
        int val;

        List<TreeNode> treeNodes; //被依赖的任务

        TreeNode(int val){
            this.val = val;
        }

        TreeNode(int val,List<TreeNode> treeNodes){
            this.treeNodes = treeNodes;
        }
    }
}
