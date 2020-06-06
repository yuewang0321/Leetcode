class Solution {
    // 88. Merge Sorted Array
    // Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
    // Note:
    // The number of elements initialized in nums1 and nums2 are m and n respectively.
    // You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
    // 三个指针i,j,k分别指向nums1元素结尾，nums2结尾，nums1数组结尾
    // 依次向前遍历，比较大小，插入nums1
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int end1 = m-1, end2 = n-1;
        int end=m+n-1;
        
        while (end1>=0 && end2>=0) {
            if (nums1[end1] < nums2[end2]) {
                nums1[end] = nums2[end2];
                end2--;
            }
            else {
                nums1[end] = nums1[end1];
                end1--;
            }
            end--;
        }
        
        while (end2>=0) {
            nums1[end] = nums2[end2];
            end--;
            end2--;
        }
    }

    // 349. Intersection of Two Arrays
    // Given two arrays, write a function to compute their intersection.
    // sort两个array
    // 两指针分别遍历两个数组比较大小，如果两指针指向的元素相等，且与result中前一个元素不相等，加入result
    public int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> result = new ArrayList<Integer>();
        int ptr1 = 0, ptr2 = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        while (ptr1<nums1.length && ptr2<nums2.length) {
            // System.out.printf("%d, %d\n", ptr1, ptr2);
            if (nums1[ptr1]==nums2[ptr2]) {
                if (result.size()==0 || result.get(result.size()-1) != nums1[ptr1]) {
                    result.add(nums1[ptr1]);
                }
                ptr1++;
                ptr2++;
            }
            else if (nums1[ptr1]<nums2[ptr2]) ptr1++;
            else ptr2++;
        }
        
        int[] array = result.stream().mapToInt(i->i).toArray();
        return array;
    }
    //faster
    public int[] intersection2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0;
        int j = 0;
        int index = 0;
        int[] result = new int[nums1.length];
        while(i < nums1.length && j < nums2.length){
            if(nums1[i] == nums2[j]){
                if(index == 0 || nums1[i] != result[index-1]){
                    result[index] = nums1[i];
                    index++;
                }
                i++;
                j++;
            }
            else if(nums1[i] <nums2[j]){
                i++;
            }
            else {
                j++ ;
            }
        }
        int[] res = new int[index];
        for(int idx = 0;idx < index;idx++) {
            res[idx] = result[idx];
        }
        return res;
    }

    /* 53. Maximum Subarray
    Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
    分析
    前缀和数组prefixSum：sum[i] = SUM(nums[0~i])
    数组中从i到j的数组和：sum[i~j] = sum[j]-sum[i-1]
    以当前位置i为结尾的最大子数组是sum[i]-min(sum[0]~sum[i-1])
    所以思路就是：从前向后遍历，三个变量存储信息：
    从起点到当前元素的和，前缀和
    截止目前的最大子数组
    前面的最小和
    */
    public int maxSubArray(int[] nums) {
        int sum = 0;
        int max = Integer.MIN_VALUE;
        int min_before = 0;
        
        for (int i=0; i<nums.length; i++) {
            sum += nums[i];
            max = Math.max(max, sum-min_before);
            min_before = Math.min(min_before, sum);
        }
        return max;
    }
    //或者keep tracking加到某一位的maximum
    public int maxSubArray2(int[] nums) {
        int max = nums[0];
        for (int i=1; i<nums.length; i++) {
            nums[i] = Math.max(nums[i-1], 0) + nums[i];
            max = Math.max(nums[i], max);
        }
        return max;
    }

    // 304. Range Sum Query 2D - Immutable
    // Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its 
    // upper left corner (row1, col1) and lower right corner (row2, col2).
    // 分析：
    // 几何图案--dp[x][y] = dp[x][y-1] + dp[x-1][y] - dp[y-1][x-1] + matrix[x][y]
    // https://www.youtube.com/watch?v=MSNSqU3BnXk
    /**
     * Your NumMatrix object will be instantiated and called as such:
     * NumMatrix obj = new NumMatrix(matrix);
     * int param_1 = obj.sumRegion(row1,col1,row2,col2);
     */
    int[][] sum;
    
    public NumMatrix(int[][] matrix) {
        if (matrix.length==0 || matrix[0].length==0) return;
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        //padding
        sum = new int[m+1][n+1];
        
        for (int i=1; i<=m; i++) {
            for (int j=1; j<=n; j++) {
                sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + matrix[i-1][j-1];
            }
        }
        
        // for (int[] row : sum) {
        //     System.out.println(Arrays.toString(row));
        // }
        
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum[row2+1][col2+1] - sum[row1][col2+1] - sum[row2+1][col1] + sum[row1][col1];
    }

    // Lintcode https://www.lintcode.com/problem/minimum-subarray/description
    // Same idea as maxSubArray in leetcode
    public int minSubArray(List<Integer> nums) {
        // write your code here
        int min = nums.get(0);
        
        for (int i=1; i<nums.size(); i++) {
            nums.set(i, Math.min(nums.get(i-1), 0) + nums.get(i));
            min = Math.min(nums.get(i), min);
        }
        
        return min;
    }

    // Leetcode 209. Minimum Size Subarray Sum
    // Given an array of n positive integers and a positive integer s, 
    // find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
    // 分析：
    // 不断更新sum，如果sum>=s就从左边开始减数字，直到sum小于s再继续加更新sum
    // 两个pointer，一个在左边表示从start开始加到另一个pointer，一个每次往前走一步
    public int minSubArrayLen(int s, int[] nums) {
        if (nums.length<=0) return 0;
        
        int sum = 0;
        int start = 0;
        int min = Integer.MAX_VALUE;
        
        for (int i=0; i<nums.length; i++) {
            sum += nums[i];
            
            while (sum>=s) {
                min = Math.min(min, i-start+1);
                sum-=nums[start];
                start++;
            }
        }
        
        if (min==Integer.MAX_VALUE) return 0;
        return min;
    }

    // Leetcode 152. Maximum Product Subarray
    // Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
    // 比较三个值：nums[i], nums[i]*之前得到的最大值，nums[i]*之前得到的最小值（负数情况）
    // 取三个中最大的
    public int maxProduct(int[] nums) {
        if (nums.length<=0) return 0;
        
        int max = nums[0];
        int currentMax = nums[0];
        int currentMin = nums[0];
        
        for (int i=1; i<nums.length; i++) {
            int newMax = Math.max(nums[i], Math.max(nums[i]*currentMax, nums[i]*currentMin));
            int newMin = Math.min(nums[i], Math.min(nums[i]*currentMax, nums[i]*currentMin));
            currentMax = newMax;
            currentMin = newMin;
            max = Math.max(max, currentMax);
        }
        
        return max;
    }

    /* Leetcode 560. Subarray Sum Equals K
    Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
    利用HashMap，存储前缀和的值和出现的次数，当有相同的前缀和出现时，result增加的数量就是当前map里面改值出现的次数。
    */
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int result = 0;
        int sum = 0;
        map.put(0, 1);
        
        for (int i=0; i<nums.length; i++) {
            sum += nums[i];
            
            if (map.containsKey(sum-k)) {
                result += map.get(sum-k);
            }
            if (map.containsKey(sum)) {
                map.put(sum, map.get(sum)+1);
            }
            else {
                map.put(sum, 1);
            }
        }
        return result;
    }

    /* Leetcode 525. Contiguous Array
    Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
    计算从0到i位中0的个数zeroSum和1的个数oneSum，以及0比1多多少zeroMoreThanOne
    利用hashmap将zeroMoreThanOne和index存储起来，
    当遇到zeroMoreThanOne == 0或者map.containsKey(zeroMoreThanOne )时说明遇到了01数量相等的子数组，记录最大长度
    */
    public int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int zeros=0, ones=0;
        int zeroMoreThanOne = 0;
        int maxLen = 0;
        map.put(0, -1);
        
        for (int i=0; i<nums.length; i++) {
            if (nums[i]==0) zeros++;
            else ones++;
            
            zeroMoreThanOne = zeros-ones; 
            if (map.containsKey(zeroMoreThanOne)) {
                int len = i - map.get(zeroMoreThanOne);
                maxLen = Math.max(maxLen, len);
            }
            else {
                map.put(zeroMoreThanOne, i);
            }
        }
        return maxLen;
    }

    /*674. Longest Continuous Increasing Subsequence
    Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).
    从前向后遍历，两个变量maxlen，len 分别记录全局最长递增子数组和局部最长递增子数组的长度，如果
    while inums[i]>nums[i-1]，len++
    更新maxlen
    index++
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums.length<=0) return 0;
        
        int maxLen = 1;
        int idx = 1;
        while (idx<nums.length) {
            int len = 1;
            while (idx<nums.length && nums[idx]>nums[idx-1]) {
                len++;
                idx++;
            }
            maxLen = Math.max(maxLen, len);
            idx+=1;
        }
        return maxLen;
    }
    /*从前向后遍历，两个变量maxlen，len_local 分别记录全局最长递增子数组和局部最长递增子数组的长度，如果
    nums[i]>nums[i-1]，len_local++,更新maxlen
    nums[i]>nums[i-1]，len_local重置为1*/

    public int findLengthOfLCIS2(int[] nums) {
        if(nums.length == 0){
            return 0; 
        }
        int maxlen = 1;
        int len_local = 1;
        for(int i = 1 ; i < nums.length;i++){
            if(nums[i] > nums[i-1]){
                len_local++;
                maxlen = Math.max(maxlen,len_local);
            }
            else{
                len_local = 1;
            }
        }
        return maxlen;
    }

    /*
    697. Degree of an Array
    Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.
    Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums. 
    maxcount是出现频率 degree是最小的subarrray长度
    两个hashmap，一个用来存储数字第一次出现的地方，另一个存储数字出现的频率
    如果有相同频率，更新degree，取两个的最小值
    如果频率大于当前最大频率，直接更新degree
    i-first.get(nums[i])+1为当前频率，eg 第一次出现为2，现在i=5,则5-2+1=4，subarray的长度为4
    */
    public int findShortestSubArray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> first = new HashMap<>();
        int maxCount = 0;
        int degree = Integer.MAX_VALUE;
        
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.replace(nums[i], map.get(nums[i])+1);
            }
            else {
                first.put(nums[i], i);
                map.put(nums[i], 1);
            }
            
            if (map.get(nums[i]) > maxCount) {
                maxCount = Math.max(maxCount, map.get(nums[i]));
                degree = i - first.get(nums[i]) + 1;
            } else if (map.get(nums[i]) == maxCount) {
                degree = Math.min(degree, i - first.get(nums[i]) + 1);
            }
        }
        return degree;
    }

    /*Leetcode 11. Container With Most Water
    Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). 
    n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
    Find two lines, which together with x-axis forms a container, such that the container contains the most water.
    若选取i,j作为边界，能够容纳的水量是：(j−i)∗min(ai,aj)
    初始化：i指向height[0],j指向height[len-1]
    两指针由外向内移动，记录最大的容水量：
    如果height[i] < height[j],i++
    如果height[i] >= height[j],j—-
    */
    public int maxArea(int[] height) {
        int left=0, right=height.length-1;
        int maxVolume = 0;
        
        while (left<right) {
            maxVolume = Math.max(maxVolume, Math.min(height[left], height[right]) * (right-left));
            if (height[left]<height[right]) left++;
            else right--;
        }  
        return maxVolume;   
    }

    /*Leetcode 565. Array Nesting
    A zero-indexed array A of length N contains all integers from 0 to N-1. 
    Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
    Suppose the first element in S starts with the selection of element A[i] of index = i, 
    the next element in S should be A[A[i]], and then A[A[A[i]]]… 
    By that analogy, we stop adding right before a duplicate element occurs in S.

    Input: A = [5,4,0,3,1,6,2]
    Output: 4
    Explanation: 
    A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
    One of the longest S[K]:
    S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}

    找最长的cycle，这个list里有很个cycle，每个cycle里的element不重复，因为是all integers form 0 to N-1, no duplicates
    所以go thorugh整个list，找cycle，visit过的记录为-1，记录最大长度
    */
    public int arrayNesting(int[] nums) {
        int max = 0;
        
        for (int i=0; i<nums.length; i++) {
            int j=i;
            int len=0;
            while (nums[j]!=-1) {
                len++;
                int temp = nums[j];
                nums[j] = -1;
                j = temp;
            }
            max = Math.max(max, len);
        }
        return max;
    }

    /* Leetcode 724. Find Pivot Index
    Given an array of integers nums, write a method that returns the "pivot" index of this array.
    We define the pivot index as the index where the sum of all the numbers to the left of the 
    index is equal to the sum of all the numbers to the right of the index.
    If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most pivot index.
    */
    // 先计算数组的全部元素和sum ，再遍历一次，prefixSum是左边的和，sum-prefixSum-nums[i]是右边的和，二者相等就返回i,否则返回-1
    public int pivotIndex(int[] nums) {
        int sum = 0;
        for (int i:nums) {
            sum+=i;
        }

        int prefixSum = 0;
        for (int i=0; i<nums.length; i++) {
            if (prefixSum == sum-prefixSum-nums[i]) {
                return i;
            }
            prefixSum += nums[i];
        }
        return -1;
    }

    /* Leetcode 238. Product of Array Except Self
    Given an array nums of n integers where n > 1,  return an array output such that output[i] 
    is equal to the product of all the elements of nums except nums[i].
    Input:  [1,2,3,4]
    Output: [24,12,8,6]
    计算element左边的product和右边的product
    prodLeft = [1,1,2,6]
    prodRight = [24,12,4,1]
    再把对应的数字乘起来
    */
    public int[] productExceptSelf(int[] nums) {
        int[] prodLeft = new int[nums.length];
        int[] prodRight = new int[nums.length];
        
        prodLeft[0] = 1;
        prodRight[nums.length-1] = 1;
        
        int[] result = new int[nums.length];
        
        for (int i=1; i<nums.length; i++) {
            prodLeft[i] = nums[i-1] * prodLeft[i-1];
        }
        
        for (int i=nums.length-2; i>=0; i--) {
            prodRight[i] = nums[i+1] * prodRight[i+1];
        }
        
        for (int i=0; i<nums.length; i++) {
            result[i] = prodLeft[i] * prodRight[i];
        } 
        return result;
    }
}