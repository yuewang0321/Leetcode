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

    /* Leetcode 46. Permutations
    Given a collection of distinct integers, return all possible permutations.

    [1,2,3]
    [1] -> [1,2] -> [1,2,3] -> delete 3 -> delete 2 -> [1,3] -> [1,3,2] -> delete 2 -> delete 3 -> delete 1
    [2] and [3] are the same
    */
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        helper(nums, new ArrayList<>());
        return result;
    }
    
    public void helper(int[] nums, List<Integer> temp) {
        if (temp.size()==nums.length) {
            result.add(new ArrayList<>(temp));
        }
        else {
            for (int i=0; i<nums.length; i++) {
                if (temp.contains(nums[i])) continue;
                else {
                    temp.add(nums[i]);
                    helper(nums, temp);
                    temp.remove(temp.size()-1);
                }
            }
        }
    } 

    /* Leetcode 47. Permutations II
    Given a collection of numbers that might contain duplicates, return all possible unique permutations.

    same as permuation, but add a list of booleans to keep tracking if the value is already used
    */
    List<List<Integer>> unique = new ArrayList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        Boolean[] used = new Boolean[nums.length]; // initialize a boolean array
          for(int i = 0; i < used.length; i++) {
             used[i] = false;
          }
        helper(nums, new ArrayList<>(), used);
        
        return unique;
    }
    
    public void helper(int[] nums, List<Integer> temp, Boolean[] used) {
        if (temp.size()==nums.length && !unique.contains(temp)) {
            unique.add(new ArrayList<>(temp));
        }
        else {
            for (int i=0; i<nums.length; i++) {
                if (used[i]) continue;
                else {
                    temp.add(nums[i]);
                    used[i] = true;
                    helper(nums, temp, used);
                    temp.remove(temp.size()-1);
                    used[i] = false;
                }
            }
        }
    }
}
