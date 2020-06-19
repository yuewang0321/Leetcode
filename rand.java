class Solution {
    /* Leetcode 1300. Sum of Mutated Array Closest to Target
    Given an integer array arr and a target value target, return the integer value such that when we change 
    all the integers larger than value in the given array to be equal to value, the sum of the array gets as close as possible 
    (in absolute difference) to target.
    In case of a tie, return the minimum such integer.
    Notice that the answer is not neccesarilly a number from arr.
    
    the result is between [0, max(arr)]
    so use two pointers to do binary search, keep tracking of the minimum number
    */
    public int findBestValue(int[] arr, int target) {
        if (arr.length<=0) return 0;
        Arrays.sort(arr);
        int n = arr.length;
        int small=0, big=arr[n-1];
        int mid = -1;
        int prev = Integer.MAX_VALUE;
        int diff = Integer.MAX_VALUE;
        
        while (small<=big) {
            mid = small + (big-small)/2;
            int sum = 0;
            for (int i=0; i<n; i++) {
                if (arr[i]<=mid) sum+=arr[i];
                else sum+=mid;
            }
            // System.out.printf("%d, %d, %d, %d\n", small, big, mid, sum);
            // System.out.printf("%d, %d\n", diff, Math.abs(target-sum));
            if (Math.abs(target-sum) < diff) {
                diff = Math.abs(target-sum);
                prev = mid;
            }
            else if(Math.abs(target-sum) == diff) prev = Math.min(prev, mid);
            
            if (sum==target) return mid;
            else if (sum>target) big = mid-1;
            else if (sum<target) small = mid+1;
        }
        return prev;
    }
}
