package algorithm.easy;

import algorithm.easy.LeetCode104.TreeNode;

/**
 * @Author: xjjiang
 * @Data: 2022/12/28 21:19
 * @Description:给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * Related Topics
 * 树
 * 深度优先搜索
 * 广度优先搜索
 * 二叉树
 *
 * 👍 1452
 * 👎 0
 */
public class LeetCode104 {

    public static void main(String[] args) {
        TreeNode tree0 = new TreeNode(15);
        TreeNode tree1 = new TreeNode(7);
        TreeNode tree2 = new TreeNode(20,tree0,tree1);
        TreeNode tree3 = new TreeNode(9);
        TreeNode tree4 = new TreeNode(3,tree3,tree2);
        int i = maxDepth(tree4);
        System.out.println(i);
    }

    public static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }else {
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            int max = Math.max(leftDepth, rightDepth);
            return max+1;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
