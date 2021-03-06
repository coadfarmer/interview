package main.java.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xjjiang
 * @Data: 2022/6/4 16:05
 * @Description: 排序
 */
public class Sort {

    List<Integer> list = new ArrayList<>();

    @Test
    public void test() {

//        int[] nums = {13, 96, 17, 86, 35, 22};
        for (int i = 0; i < 6; i++) {
            list.add((int) (Math.random() * 10));
        }
        int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(nums));
//        quickSort(nums, 0, nums.length - 1);

        heapSort(nums, nums.length);
        System.out.println(Arrays.toString(nums));

    }

    /**
     * 快速排序
     *
     * @param nums
     * @param i
     * @param j
     */
    public void quickSort(int[] nums, int i, int j) {
        if (i >= j)
            return;
        int baseNum = nums[i];
        int left = i;
        int right = j;
        while (i < j) {
            while (nums[j] >= baseNum && i < j)
                j--;
            while (nums[i] <= baseNum && i < j)
                i++;
            swap(nums, i, j);
        }
        swap(nums, left, j);
        System.out.println("left:" + left);
        System.out.println("i:" + i);
        System.out.println("j:" + j);
        quickSort(nums, left, j - 1);
        quickSort(nums, j + 1, right);
    }

    /**
     * 堆排序
     * <p>
     * 交换最后一个节点和根节点（最大值），取走最后一个节点，并对新的树重新堆化
     * <p/>
     *
     * @param nums   要排序的数组
     * @param length 数组长度
     */
    public void heapSort(int[] tree, int length) {
        buildHeap(tree, length);
        for (int i = length - 1; i >= 0; i--) {
            swap(tree, i, 0);
            heapify(tree, i, 0);
        }
    }

    /**
     * 堆化
     * <p>
     * 堆的定义
     * <p>1. 是一颗完全二叉树（每次添加节点都在最底层的最左边）</p>
     * <p>2. 父节点一定大于子节点</p>
     * </p>
     * <p>
     * 如果用一个数组nums[i]从上到下, 从左到右描述这个堆
     * <p>
     * 1. 父节点parent = (i-1)/2; i不是根节点
     * </p>
     * <p>
     * 2. 左子节点c1 = 2i + 1;
     * </p>
     * <p>
     * 3. 右子节点c2 = 2i + 2;
     * </p>
     * </p>
     *
     * @param tree   需要被堆化的树
     * @param length 数组长度
     * @param i      对第i个节点做堆化
     */
    public void heapify(int[] tree, int length, int i) {
        if (i >= length)
            return;
        int c1 = 2 * i + 1;
        int c2 = 2 * i + 2;
        int max = i;
        if (c1 < length && tree[c1] > tree[max])
            max = c1;
        if (c2 < length && tree[c2] > tree[max])
            max = c2;
        if (max != i) {
            swap(tree, i, max);
            heapify(tree, length, max);
        }

    }

    /**
     * 从最后一个节点的父节点开始堆化
     *
     * @param tree   需要堆化的树
     * @param length tree的长度
     */
    public void buildHeap(int[] tree, int length) {
        int lastNode = length - 1;
        int parent = (lastNode - 1) / 2;
        for (int i = parent; i >= 0; i--) {
            heapify(tree, length, i);
        }
    }

    public void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;

    }

}
