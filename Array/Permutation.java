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


    /* Leetcode 39. Combination Sum
    Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), 
    find all unique combinations in candidates where the candidate numbers sums to target.
    The same repeated number may be chosen from candidates unlimited number of times.
    Input: candidates = [2,3,6,7], target = 7,
    A solution set is:
    [
    [7],
    [2,2,3]
    ]

    same as above, use backtarcking
    but sort the array first then start searching from index i, to make sure we don't generate all permutations, just getting what we need.
    becase [2,2,3] [2,3,2] [3,2,2] are the same in this case.
    */
    List<List<Integer>> combinationSum = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        helper(candidates, target, new ArrayList<>(), 0);
        return combinationSum;
    }
    public void helper(int[] candidates, int target, List<Integer> temp, int start) {
        if (target==0) combinationSum.add(new ArrayList<>(temp));
        else {
            for (int i=start; i<candidates.length; i++) {
                if (target-candidates[i]>=0) {
                    temp.add(candidates[i]);
                    helper(candidates, target-candidates[i], temp, i);
                    temp.remove(temp.size()-1);
                }
            }
        }
    }

    /* Leetcode 40. Combination Sum II
    Given a collection of candidate numbers (candidates) and a target number (target), 
    find all unique combinations in candidates where the candidate numbers sums to target.
    Each number in candidates may only be used once in the combination.

    Input: candidates = [10,1,2,7,6,1,5], target = 8,
    A solution set is:
    [
        [1, 7],
        [1, 2, 5],
        [2, 6],
        [1, 1, 6]
    ]

    用上一题的思路，加上之前used boolean list to keep tracking if the element is already used.
    */
    List<List<Integer>> combinationSum2 = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Boolean[] used = new Boolean[candidates.length];
        Arrays.sort(candidates);
        for (int i=0; i<candidates.length; i++) {
            used[i] = false;
        }
        helper(candidates, target, new ArrayList<>(), used, 0);
        return combinationSum2;
    }
    
    public void helper(int[] candidates, int target, List<Integer> temp, Boolean[] used, int start) {
        if (target==0 && !combinationSum2.contains(temp)) {
            combinationSum2.add(new ArrayList<>(temp));
        }
        else {
            for (int i=start; i<candidates.length; i++) {
                if (target-candidates[i]>=0 && !used[i]) {
                    used[i] = true;
                    temp.add(candidates[i]);
                    helper(candidates, target-candidates[i], temp, used, i);
                    temp.remove(temp.size()-1);
                    used[i] = false;
                }
            }
        }
    }

    /* Leetcode 216. Combination Sum III
    Find all possible combinations of k numbers that add up to a number n, 
    given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
    Input: k = 3, n = 7
    Output: [[1,2,4]]

    same backtracking idea, first generate nums list, then find permutations of length k that adds up to n
    note that only numbers from 1 to 9 can be used and should be unique.
    */
    List<List<Integer>> combinationSum3 = new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        int limit = Math.min(9, n);
        int[] nums = new int[limit];
        for (int i=1; i<limit+1; i++) {
            nums[i-1] = i;
        }
        helper(nums, k, n, 0, new ArrayList<>(), 0);
        return combinationSum3;
    }
    
    public void helper(int[] nums, int k, int n, int sum, List<Integer> temp, int start) {
        if (temp.size()==k && sum==n) combinationSum3.add(new ArrayList<>(temp));
        else {
            for (int i=start; i<nums.length; i++) {
                if (temp.contains(nums[i])) continue;
                else {
                    temp.add(nums[i]);
                    helper(nums, k, n, sum+nums[i], temp, i);
                    temp.remove(temp.size()-1);
                }
            }
        }
    }

    /* Leetcode 377. Combination Sum IV
    Given an integer array with all positive numbers and no duplicates, 
    find the number of possible combinations that add up to a positive integer target.
    nums = [1, 2, 3]
    target = 4

    The possible combination ways are:
    (1, 1, 1, 1)
    (1, 1, 2)
    (1, 2, 1)
    (1, 3)
    (2, 1, 1)
    (2, 2)
    (3, 1)


    the above idea will have time limit exceed error

    this could be solved using dp

    Think about the recurrence relation first. How does the # of combinations of the target related to the 
    # of combinations of numbers that are smaller than the target?

    So we know that target is the sum of numbers in the array. Imagine we only need one more number to reach target, 
    this number can be any one in the array, right? So the # of combinations of target, 
    comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].

    In the example given, we can actually find the # of combinations of 4 with the # of combinations of 
    3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

    Then think about the base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.

    EDIT: The problem says that target is a positive integer that makes me feel it's unclear to put it in the above way. Since target == 0 only happens when in the previous call, target = nums[i], we know that this is the only combination in this case, so we return 1.
    */
    int[] dp;
    public int combinationSum4(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return helper(nums, target);
    }
    
    public int helper(int[] nums, int target) {
        if (dp[target]!=-1) return dp[target];

        int res = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i]<=target) {
                res += helper(nums, target-nums[i]);
            }
        }
        dp[target] = res;
        return res;
    }
}
