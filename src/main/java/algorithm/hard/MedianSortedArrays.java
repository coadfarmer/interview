package algorithm.hard;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jwx1186440
 * @since 2022年07月11日
 */
public class MedianSortedArrays {

    /**
     * 上下文切换法
     *
     * @param nums1
     * @param nums2
     * @return 中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if (null == nums1 || null == nums2) {
            return 0.0;
        }

        //当数组中有数组为空数组的情况
        if (nums1.length == 0 || nums2.length == 0) {
            if (nums1.length > 0) {
                nums2 = nums1;
            }
            if (nums2.length > 0) {
                if ((nums2.length & 1) == 0) {
                    int pre = nums2.length > 1 ? (nums2.length >> 1) - 1 : 0;
                    return (nums2[pre] + nums2[(nums2.length >> 1)]) / 2.0;
                } else {
                    return nums2[(nums2.length >> 1)];
                }
            }
        }

        int p1 = 0; //记录nums1的下标
        int p2 = 0; //记录nums2的下标
        int p = 0; //记录移动位数
        int tag = nums1[0]<nums2[0]?0:1; //记录当前指针是哪个数组 0-nums1，1-nums-2
        int pre_num = Math.min(nums1[0], nums2[0]);//记录上一个指针指向哪个数组 0-nums1, 1-nums2
        int pre_back = 0;
        int m = (nums1.length + nums2.length) >> 1; //记录中位数的下标
        m = Math.max(m, 0);
        while (p < m) {
            pre_num = tag == 0 ? nums1[p1] : nums2[p2];
            if (nums1[p1] < nums2[p2]) {
                if (p1 < nums1.length - 1) {
                    p1++;
                    tag = resetTag(nums1[p1],nums2[p2]);
                } else if (p2 < nums2.length - 1) {
                    tag = 1;
                    if(nums2[p2]<nums1[p1]){
                        p2++;
                    }
                }
            } else if (nums1[p1] > nums2[p2]) {
                if (p2 < nums2.length - 1) {
                    p2++;
                    tag = resetTag(nums1[p1],nums2[p2]);
                } else if (p1 < nums1.length - 1) {
                    tag = 0;
                    if(nums1[p1]<nums2[p2]){
                        p1++;
                    }
                }
            } else {
                if (p1 < nums1.length - 1 && p2 < nums2.length - 1) {
                    p1++;
                    p2++;
                    if (nums1[p1] > nums2[p2]) {
                        p1--;
                        tag = 1;
                    } else if (nums1[p1] < nums2[p2]) {
                        p2--;
                        tag = 0;
                    } else if (nums1[p1] == nums2[p2]) {
                        if(pre_back == 0){
                            p1--;
                            tag = 1;
                            pre_back = 1;
                        }else {
                            p2--;
                            tag = 0;
                            pre_back = 0;
                        }

                    }
                } else if (p1 < nums1.length - 1) {
                    p1++;
                    tag = 0;
                } else if (p2 < nums2.length - 1) {
                    p2++;
                    tag = 1;
                }
            }
            p++;
        }
        //判断奇偶
        if (((nums1.length + nums2.length) & 1) == 0) {
            //偶数
            double medium = tag == 0 ? nums1[p1] : nums2[p2];
            return (medium + pre_num) / 2.0;
        } else {
            //奇数
            return tag == 0 ? nums1[p1] : nums2[p2];
        }

    }

    public int resetTag(int a, int b) {
        return a < b ? 0 : 1;
    }

    /**
     * 插入排序归一法
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        List<Integer> integerList1 = Arrays.stream(nums1).boxed().collect(Collectors.toList());
        List<Integer> integerList2 = Arrays.stream(nums2).boxed().collect(Collectors.toList());
        integerList1.addAll(integerList2);
        List<Integer> collect = integerList1.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        double medium;
        int length = collect.size();
        if (isParity(length)) {

            medium = (collect.get(length >> 1) + collect.get((length >> 1) - 1)) / 2.0;
        } else {
            medium = collect.get((length - 1) >> 1);
        }
        return medium;
    }

    @Test
    public void test() {
        int[] nums1 = {2};
        int[] nums2 = {2};
        double v = findMedianSortedArrays(nums1, nums2);
        System.out.println(v);

    }

    /**
     * @param num
     * @return true-偶数 false-奇数
     */
    public boolean isParity(int num) {
        return (num & 1) == 0;
    }

}
