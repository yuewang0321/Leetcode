class Solution {
    /* Leetcode 1. Two Sum
    Given an array of integers, return indices of the two numbers such that they add up to a specific target.
    You may assume that each input would have exactly one solution, and you may not use the same element twice.
    遍历，用hash表存储下来，然后遍历数组i,在hash表中查找是否有target-i
    */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] ret = new int[2];
        
        for (int i=0; i<nums.length; i++) {
            if (!map.containsKey(target-nums[i])) {
                map.put(nums[i], i);
            }
            else {
                ret[0] = map.get(target-nums[i]);
                ret[1] = i;
            }
        }
        return ret;
    }

    /* Leetcode 167. Two Sum II - Input array is sorted
    Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
    The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.
    Note:
        Your returned answers (both index1 and index2) are not zero-based.
        You may assume that each input would have exactly one solution and you may not use the same element twice.
     也可以用hashmap，或者两个pointer
    步骤：
        1. 排序，需要把元素在原来数组中的idx存下来
        2. 两个指针i,j，i指向头，j指向尾
        3.if(nums[i]+nums[j] < targert) i++
        if(nums[i]+nums[j] > targert) j--
        if(nums[i]+nums[j] == targert) return
    */
    public int[] twoSum_sorted(int[] numbers, int target) {
        int start=0, end=numbers.length-1;
        int[] ret = new int[2];
        
        while (start<end) {
            if (numbers[start]+numbers[end]<target) start++;
            else if (numbers[start]+numbers[end]>target) end--;
            else {
                ret[0] = start+1;
                ret[1] = end+1;
                break;
            }
        }
        return ret;    
    }

    /* Leetcode 15. 3Sum
    Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? 
    Find all unique triplets in the array which gives the sum of zero.
    固定a ， 然后对b + c利用Two Sum方法
    需要注意的是遍历时要跳过重复的元素
    */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int a=0; a<=nums.length-3; a++) {
            //check for duplicate elements
            if (a>0 && nums[a]==nums[a-1]) continue;
            
            int b = a+1;
            int c = nums.length-1;
            while (b<c) {
                if (nums[a] + nums[b] + nums[c] == 0) {
                    ArrayList<Integer> result = new ArrayList<>();
                    result.add(nums[a]);
                    result.add(nums[b]);
                    result.add(nums[c]);
                    results.add(result);
                    b++;
                    c--;  
                    //skip duplicate elements
                    while (b<c && nums[b]==nums[b-1]) b++;
                    while (b<c && nums[c]==nums[c+1]) c--;
                }
                else if (nums[a] + nums[b] + nums[c] < 0) b++;
                else c--;
            }
        }
        return results;
    }

    /* Leetcode 18. 4Sum
    Given an array nums of n integers and an integer target, 
    are there elements a, b, c, and d in nums such that a + b + c + d = target? 
    Find all unique quadruplets in the array which gives the sum of target.
    跟前面一样的思路，固定a，b对c,d做2Sum
    */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        
        for (int i=0; i<nums.length-3; i++) {
            //skip duplicates
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            
            for (int j=i+1; j<nums.length-2; j++) {
                //skip duplicates
                if (j > i+1 && nums[j] == nums[j-1]) {
                    continue;
                }
                
                int k = j+1;
                int l = nums.length-1;
                while (k<l) {
                    if(nums[i] + nums[j] + nums[k] + nums[l] == target) {
                        System.out.println("adding");
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);
                        temp.add(nums[l]);
                        result.add(temp);
                        k++;
                        l--;
                        //skip duplicates
                        while (k<l && nums[k] == nums[k-1]) k++;
                        while (k<l && nums[l] == nums[l+1]) l--;
                    }
                    
                    else if (nums[i] + nums[j] + nums[k] + nums[l] < target) k++;
                    else l--;
                }
            }
        }
        return result;
    }

    /* Leetcode 454. 4Sum II
    Given four lists A, B, C, D of integer values, 
    compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.
    AB为一组，CD为一组, 计算AB的和存入hashmap，然后计算CD时去AB的hashmap中寻找-sum出现的次数累加到result上即可。
    */
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int result = 0;
        HashMap<Integer,Integer> mapab = new HashMap<>();
        
        for (int i=0; i<A.length; i++) {
            for (int j=0; j<B.length; j++) {
                if (mapab.containsKey(A[i]+B[j])) {
                    mapab.put(A[i]+B[j], mapab.get(A[i]+B[j])+1);
                }
                else mapab.put(A[i]+B[j], 1);
            }
        }
        
        for (int k=0; k<C.length; k++) {
            for (int l=0; l<D.length; l++) {
                if (mapab.containsKey(-C[k]-D[l])) result += mapab.get(-C[k]-D[l]);
            }
        }
        return result;
    }
    
    /* Leetcode 611. Valid Triangle Number
    Given an array consists of non-negative integers, your task is to 
    count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
    构成三角形条件：两边之和大于第三边
    1 排序，两个小边之和大于第三边
    2 固定a，遍历b、c，寻找第一个nums[a] + nums[b] > nums[c]的位置
    3 bc之间的位置都可以作为c，使得nums[a] + nums[b] > nums[c]，result+= c-b。
    */
    public int triangleNumber(int[] nums) {
        int result = 0;
        Arrays.sort(nums);
        
        for (int i=0; i<nums.length-2; i++) {
            int j = i+1;
            int k = nums.length-1;
            while (j<k) {
                while(j < k && nums[i] + nums[j] <= nums[k]) k--;
                
                if (nums[i]+nums[j] > nums[k]) result += k-j;
                //下一个加上当前可能比nums.length-1大，所以要reset k
                j++;
                k = nums.length - 1;
            }
        }
        return result;
    }

    /* Leetcode 16. 3Sum Closest
    Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. 
    Return the sum of the three integers. You may assume that each input would have exactly one solution.

    use 3 pointers to point current element, next element and the last element. 
    If the sum is less than target, it means we have to add a larger element so next element move to the next. 
    If the sum is greater, it means we have to add a smaller element so last element move to the second last element.
    Keep doing this until the end. Each time compare the difference between sum and target, 
    if it is less than minimum difference so far, then replace result with it, otherwise keep iterating.
    */
    public int threeSumClosest(int[] nums, int target) {
        if (nums.length==0) return 0;
        
        Arrays.sort(nums);
        int result = nums[nums.length-1] + nums[nums.length-2] + nums[nums.length-3];
        
        for (int i=0; i<nums.length-2; i++) {
            int start = i + 1, end = nums.length - 1;
            while (start<end) {
                int temp = nums[i] + nums[start] + nums[end];
                if (temp>target) end--;
                else if (temp<target) start++;
                else if (temp==target) return temp;
                
                if (Math.abs(temp-target)<Math.abs(result-target)) result = temp;   
            }
        }
        return result;
    }
}