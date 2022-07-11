package algorithm.hard;

import org.junit.Test;

/**
 * @author jwx1186440
 * @since 2022年07月11日
 */
public class MedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if(nums1.length == 0 || nums2.length ==0){
            if(nums1.length>0){
                nums2 = nums1;
            }
            if(nums2.length > 0){
                if((nums2.length&1)==0){
                    int pre = nums2.length>1? (nums2.length>>1) -1 : 0;
                    return (nums2[pre]+nums2[(nums2.length>>1)])/2.0;
                }else {
                    return nums2[(nums2.length>>1)];
                }
            }
        }

        int p1 = 0; //记录nums1的指针
        int p2 = 0; //记录nums2的指针
        int m = (nums1.length+ nums2.length)>>1; //记录中位数的指针
        m = Math.max(m-1,0);
        while ((p1+p2)<m){
            if(nums1[p1]<nums2[p2] && p1 < nums1.length-1){
                p1++;
            }
            if(nums1[p1]>nums2[p2] && p2 < nums2.length-1){
                p2++;
            }
            if(nums1[p1] == nums2[p2] && p1 < nums1.length-1 && p2 < nums2.length-1){
                p1++;
                p2++;
            }
        }

        //判断奇偶
        if(((nums1.length+ nums2.length)&1)==0){
            //偶数
            double temp1 = Math.max(nums1[p1],nums2[p2]);
            double temp2;
            if(nums1[p1] == nums2[p2]){
                if(p1>0){
                    p1--;
                }
                if(p2>0){
                    p2--;
                }
                temp2 = Math.max(nums1[p1],nums2[p2] );
            } else {
                temp2 = Math.min(nums1[p1],nums2[p2] );
            }
            System.out.println(temp1 + " "+ temp2);
            return (temp1+temp2)/2.0;

        }else {
            //奇数
            System.out.println(p1 +" "+ p2);
            return Math.max(nums1[p1],nums2[p2]);
        }
    }

    @Test
    public void test(){
        int[] nums1 = {1,3};
        int[] nums2 = {2,7};
        double v = findMedianSortedArrays(nums1, nums2);
        System.out.println(v);
    }

}
