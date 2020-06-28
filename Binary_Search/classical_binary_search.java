class Solution {
    // 1. Search for a range
    //leetcode 34. Find First and Last Position of Element in Sorted Array
    // Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.

    // Your algorithm’s runtime complexity must be in the order of O(log n).

    // If the target is not found in the array, return [-1, -1].

    // For example,
    // Given [5, 7, 7, 8, 8, 10] and target value 8,
    // return [3, 4]

    // 分析：
    // 找到n第一次和最后一次出现的位置，返回即可
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums.length<=0) return result;

        //find first shown position
        int start = 0, end = nums.length-1;
        while (start+1<end) {
            int mid = start + (end-start)/2;
            //第一次出现的位置在前半段，end前移
            if (nums[mid]==target) end=mid;
            if (nums[mid]<target) start=mid;
            if (nums[mid]>target) end=mid;
        }
        if (nums[start]==target) result[0]=start;
        else if (nums[end]==target) result[0]=end;

        //find last shown position
        start=0;
        end=nums.length-1;
        while (start+1<end) {
            int mid = start + (end-start)/2;
            //第一次出现的位置在前半段，start后移
            if (nums[mid]==target) start=mid;
            if (nums[mid]<target) start=mid;
            if (nums[mid]>target) end=mid;
        }
        if (nums[end]==target) result[1]=end;
        else if (nums[start]==target) result[1]=start;

        return result;
    }

    // Leetcode 35 Search Insert Position
    // Given a sorted array and a target value, return the index if the target is found. 
    // If not, return the index where it would be if it were inserted in order.
    // You may assume no duplicates in the array.
    // 分析：
    // 二分法的问题一般都是找满足条件的firstposition和lastposition.
    // 这道题是需要找到firstposition >= targrt，第一个>=target的位置。
    // while结束后，需要找firstposition的话先判断start，找lastposition的话先判断end。
    public int searchInsert(int[] nums, int target) {
        int start=0, end=nums.length;
        while (start+1<end) {
            int mid = start + (end-start) / 2;
            //如果找到了target
            if (nums[mid]==target) return mid;
            if (nums[mid]<target) start=mid;
            if (nums[mid]>target) end=mid;
        }
        
        //如果没找到 在start和end中找比target大的
        if (nums[start]>target) return start;
        else if (nums[end]>target) return end;
        //如果都比target小
        else return end+1;
    }

    // Leetcode 74. Search a 2D Matrix
    // Write an efficient algorithm that searches for a value in an m x n matrix. 
    // This matrix has the following properties:
        // Integers in each row are sorted from left to right.
        // The first integer of each row is greater than the last integer of the previous row.
    // 分析：
    // 两种做法：
    // 1. 先按每行的首个数字二分确定target所在的行，再在这行里二分。
    // 2. 看成一维数组，二分查找，其中看成一维数组的序号n和二维数组中行号、列号对应关系为：
        // r = n/columns;
        // c = n%columns;
    public boolean searchMatrix(int[][] matrix, int target) {

        if (matrix.length<=0 || matrix[0].length<=0) return false;
        
        int row = matrix.length;
        int col = matrix[0].length;

        
        int start = 0, end = row*col-1;
        while (start<end-1) {
            int mid = start + (end-start)/2;
            if (matrix[mid/col][mid%col]==target) return true;
            if (matrix[mid/col][mid%col]<target) start = mid;
            if (matrix[mid/col][mid%col]>target) end = mid;
        }
        
        if(matrix[start/col][start%col]==target) return true;
        if(matrix[end/col][end%col]==target) return true;
        return false;
    }

    //Leetcode 240. Search a 2D Matrix II
    // Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
        // Integers in each row are sorted in ascending from left to right.
        // Integers in each column are sorted in ascending from top to bottom.
    //分析：
    //矩阵特点：每一行和每一列递增
    //从左上角往右下角找，有如下三种情况：
    // 元素 = target，返回true
    // 元素 < target，下一步向下走，因为右边的元素都大于当前元素，上方元素小于当前元素
    // 元素 > target，下一步向左走，因为右边的元素都大于当前元素，上方元素小于当前元素
    public boolean searchMatrixII(int[][] matrix, int target) {
        if (matrix==null || matrix.length==0) return false;
        int row = matrix.length, col = matrix[0].length;
        
        int curx = 0, cury=col-1;
        
        while (curx<row && cury>-1) {
            if (matrix[curx][cury]==target) return true;
            if (matrix[curx][cury]<target) curx++;
            else if (matrix[curx][cury]>target) cury--;
        }
        
        return false;
    }

    // Leetcode 278. First Bad Version 
    // You are a product manager and currently leading a team to develop a new product. 
    // Unfortunately, the latest version of your product fails the quality check. 
    // Since each version is developed based on the previous version, all the versions after a bad version are also bad.
    // Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, 
    // which causes all the following ones to be bad.
    // You are given an API bool isBadVersion(version) which will return whether version is bad. 
    // Implement a function to find the first bad version. You should minimize the number of calls to the API.
    public int firstBadVersion(int n) {
        int start=0, end=n;
        
        while (start<end-1) {
            int mid = start+(end-start)/2;
            if (isBadVersion(mid)) end=mid;
            else start=mid;
        }
        if (isBadVersion(start)) return start;
        if (isBadVersion(end)) return end;
        
        return -1;
    }

    // A peak element is an element that is greater than its neighbors.
    // Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.
    // The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
    // You may imagine that nums[-1] = nums[n] = -∞.
    // 分析
    // 每次找两个mid,比较大小，peak在大的一边，move start or end accordingly
    public int findPeakElement(int[] nums) {
        int start=0, end=nums.length-1;
        
        while(start<end) {
            int mid = start+(end-start)/2;
            int mid2 = mid+1;
            if (nums[mid]<nums[mid2]) start=mid2;
            else end=mid;
        }
        return start;
    }

    //Leetcode 153. Find Minimum in Rotated Sorted Array
    // Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
    // (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2])
    // Find the minimum element.
    // You may assume no duplicate exists in the array.
    // 分析
    // 递增数组：
    //             ↗
    //             ↗
    //         ↗
    //         ↗
    //     ↗
    //     ↗
    // ↗
    // ↗
    // 旋转数组：
    // 旋转数组，分成两段上升数组：
    //             ↗|
    //           ↗  |
    //         ↗    |
    //       ↗      |
    //     ↗        |
    // -------------|------------------
    //              |    ↗
    //              |  ↗
    //              |↗
    //     ↑        ↑        ↑
    //     start  mid      end
    // 用二分法，判断mid与end的大小，确定mid位于两段上升数组的哪一段：
    // 1. nums[mid] < nums[end]:
    //     mid在后段上升数组中，end = mid
    // 2. nums[mid] > nums[end]:
	// mid在前段上升数组中，start = mid
    public int findMin(int[] nums) {
        int start=0, end=nums.length-1;
        while (start<end-1) {
            int mid = start + (end-start)/2;
            if (nums[end]<nums[mid]) start=mid;
            if (nums[end]>nums[mid]) end=mid;
        }
        
        if (nums[start]<nums[end]) return nums[start];
        return nums[end];
    }

    // Leetcode 154. Find Minimum in Rotated Sorted Array II
    // Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
    // (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
    // Find the minimum element.
    // The array may contain duplicates.
    // 分析：
    // 和上一题思路一样，只是遇到duplicate的时候end向前移动一位
    public int findMinWithDup(int[] nums) {
        int start=0, end=nums.length-1;
        while (start<end-1) {
            int mid = start + (end-start)/2;
            if (nums[end]<nums[mid]) start=mid;
            else if (nums[end]>nums[mid]) end=mid;
            else end--;
        }
        if (nums[start]<nums[end]) return nums[start];
        else return nums[end];
    }

    // Leetcode 33. Search in Rotated Sorted Array
    // Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
    // (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
    // You are given a target value to search. If found in the array return its index, otherwise return -1.
    // You may assume no duplicate exists in the array.
    // Your algorithm's runtime complexity must be in the order of O(log n).
    // 分析：
    // 先判断mid处于第一还是第二上升区域，再根据target落在的区间调整start和end
    public int search(int[] nums, int target) {
        if (nums.length<=0) return -1;
        int start=0, end=nums.length-1;
        
        while (start<end-1) {
            int mid = start + (end-start)/2;
            if (nums[mid]==target) return mid;
            
            if (nums[mid]<nums[end]) {
                if (target>=nums[mid] && target<=nums[end]) start=mid;
                else end=mid;
            }
            else if (nums[mid]>nums[end]) {
                if (target>=nums[start] && target<=nums[mid]) end=mid;
                else start=mid;
            }
        }
        if (nums[start]==target) return start;
        else if (nums[end]==target) return end;
        else return -1;
    }

    //81. Search in Rotated Sorted Array II
    // Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
    // (i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).
    // You are given a target value to search. If found in the array return true, otherwise return false.
    // 分析：
    // 类似于前两道题目，遇到duplicate的时候end向前移动一位
    // worst case会变成O(n)
    public boolean searchWithDup(int[] nums, int target) {
        if (nums.length<=0) return false;
        int start=0, end=nums.length-1;
        
        while (start<end-1) {
            int mid = start + (end-start)/2;
            if (nums[mid]==target) return true;
            
            if (nums[mid]<nums[end]) {
                if (target>=nums[mid] && target<=nums[end]) start=mid;
                else end=mid;
            }
            else if (nums[mid]>nums[end]) {
                if (target>=nums[start] && target<=nums[mid]) end=mid;
                else start=mid;
            }
            else end--;
        }
        if (nums[start]==target || nums[end]==target) return true;
        return false;
    }

    /* Leetcode 1482. Minimum Number of Days to Make m Bouquets
    Given an integer array bloomDay, an integer m and an integer k.
    We need to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
    The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.
    Return the minimum number of days you need to wait to be able to make m bouquets from the garden. 
    If it is impossible to make m bouquets return -1.

    binary search, left = 1 and right = 10^9 according to question
    given mid days, count how many flowers blooms, and how many bouquets we can get
    if bouquets < m, left = mid+1
    else right = mid
    */
    public int minDays(int[] bloomDay, int m, int k) {
        if (m*k>bloomDay.length) return -1;
        
        int left = 1, right = (int)Math.pow(10, 9);
        
        while (left<right) {
            int mid = left + (right-left)/2;
            int flowers = 0, bouquets = 0;
            
            for (int bloom: bloomDay) {
                if (bloom<=mid) {
                    flowers+=1;
                    if (flowers>=k) {
                        flowers=0;
                        bouquets+=1;
                    }
                } else {
                    flowers=0;
                }
            }
            if (bouquets<m) left = mid+1;
            else right = mid;
        }
        return left;
    }

}
