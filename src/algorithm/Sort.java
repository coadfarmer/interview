package algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xjjiang
 * @Data: 2022/6/4 16:05
 * @Description:
 */
public class Sort {

    List<Integer> list = new ArrayList<>();

    @Test
    public void test() {


        for (int i = 0; i < 5; i++) {
            list.add((int) (Math.random() * 100));
        }
        int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(nums));
        quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));

    }

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

    public void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;

    }

}
