class Solution {
    // 不开额外的空间，用两个指针分成两个、三个部分，利用quicksort的思想

    /* Lintcode 31. Partition Array
    Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:
    All elements < k are moved to the left
    All elements >= k are moved to the right
    Return the partitioning index, i.e the first index i nums[i] >= k.
    利用quicksort的思想，两指针一前一后向中间遍历，前面遇到大的，后面遇到小的交换，最后判断nums[j]和target的大小关系，返回结果。
    */
    public int partitionArray(int[] nums, int k) {
        // write your code here
        if (nums.length==0) return 0;
        
        int i = 0, j = nums.length-1;
        while (i<j) {
            while (i<j && nums[i]<k) i++;
            while (i<j && nums[j]>=k) j--;
            
            if (i<j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        
        if (nums[j] >= k) return j;  
        return j+1;
    }

    /* Leetcode 75. Sort Colors
    Given an array with n objects colored red, white or blue, 
    sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
    Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
    两次partition，第一次左边<1，右边>=1; 第二次左边<2，右边>=2
    */
    public void sortColors(int[] nums) {
        int partition = 1;
        int i = 0, j = nums.length-1;
        
        while (i<j) {
            while (i<j && nums[i]<partition) i++;
            while (i<j && nums[j]>=partition) j--;
            if (i<j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        
        j = nums.length-1;
        partition = 2;
        while (i<j) {
            while (i<j && nums[i]<partition) i++;
            while (i<j && nums[j]>=partition) j--;
            if (i<j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
    }

    /* Lintcode 143. Sort Colors II
    Given an array of n objects with k different colors (numbered from 1 to k), 
    sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, ... k.
    跟上一题一样，用while loop把partition的值每次+1
    */
    public void sortColors2(int[] colors, int k) {
        // write your code here
        int i = 0, j = colors.length-1;
        int count = 1;
        
        while (count<=k) {
            while (i<j) {
                while (i<j && colors[i]<count) i++;
                while (i<j && colors[j]>=count) j--;
                if (i<j) {
                    int temp = colors[i];
                    colors[i] = colors[j];
                    colors[j] = temp;
                }
            }
            count++;
            j = colors.length-1;
        }
    }

    
}