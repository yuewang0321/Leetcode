class Solution {
    /* Leetcode 31. Next Permutation
    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
    The replacement must be in-place and use only constant extra memory.
    Example: 123->132   321->123    115->151    11->11

    i和j指向倒数第一个和第二个数，一直往前走直到找到nums[i]<nums[j]， 这时候从i+1到最后是递减数列
    从i的右边找，从后往前，找到第一个比i大的数字，使数字增大幅度最小，swap
    再把i+1到最后reverse一下,得到递增数列，使后面的值最小，得到答案

    124653 -> 125643 -> 125346
    */
    public void nextPermutation(int[] nums) {
        if (nums.length<=0) return;
        int i=nums.length-2, j = nums.length-1;
        while (i>=0 && nums[i]>=nums[j]){
            i--;
            j--;
        }
        
        if (i>=0) {
            j = nums.length-1;
            while (nums[j] <= nums[i]) j--;
            swap(nums, i, j);
        }
        reverse(nums, i+1, nums.length-1);
    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public void reverse(int[] nums, int start, int end) {
        while (start<end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
}
