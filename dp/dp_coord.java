class Solution {
    /* Leetcode 64. Minimum Path Sum
    Given a m x n grid filled with non-negative numbers, 
    find a path from top left to bottom right which minimizes the sum of all numbers along its path.

    从右下角往上计算到每一个number的最小path
    result[i][j] = grid[i][j] + Math.min(result[i][j+1], result[i+1][j])
    要先fill in last row & col
    */
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[grid.length-1].length;
        int[][] result = new int[row][col];
        result[row-1][col-1] = grid[row-1][col-1];
        
        for (int i=row-2; i>=0; i--) {
            result[i][col-1] = grid[i][col-1] + result[i+1][col-1];
        }
        for (int i=col-2; i>=0; i--) {
            result[row-1][i] = grid[row-1][i] + result[row-1][i+1];
        }

        for (int i=row-2; i>=0; i--) {
            for (int j=col-2; j>=0; j--) {
                result[i][j] = grid[i][j] + Math.min(result[i][j+1], result[i+1][j]);
            }
        }
        return result[0][0];
    }
    /* 或者从上往下计算也可以
    状态：f[i,j]表示从(0,0)出发走到(i,j)的路径长度
    方程：f[i,j] = a[i,j] + min(f[i-1,j],f[i,j-1]),只能从左边和上边走过来
    初始化：初始化二维数组时，初始化第0行和第0列
    答案：f[end,end]
    */
    public int minPathSum2(int[][] grid) {
        int[][] cost = new int[grid.length][grid[0].length];
        
        cost[0][0] = grid[0][0];
        for (int i=1; i<grid.length; i++) {
            cost[i][0] = cost[i-1][0] + grid[i][0];
        }
        for (int i=1; i<grid[0].length; i++) {
            cost[0][i] = cost[0][i-1] + grid[0][i];
        }
        
        for (int i=1; i<grid.length; i++) {
            for (int j=1; j<grid[0].length; j++) {
                cost[i][j] = Math.min(cost[i-1][j]+grid[i][j], cost[i][j-1]+grid[i][j]);
            }
        }
        return cost[grid.length-1][grid[0].length-1];
    }

    /* Leetcode 62. Unique Paths
    A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
    The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid
    状态：f[i,j]：从(0,0)到(i,j)的方案个数
    方程：f[i,j] = f[i-1,j]+f[i,j-1]，走到[i,j]有两种方式，从[i-1,j]和从[i,j-1]，两种方式方案数加和为走到[i,j]点的总方案数
    初始化：第0行和第0列的方案数为1，f[i,0]=f[0,j]=1
    结果：f[m,n]，右下角元素的状态值
    */
    public int uniquePaths(int m, int n) {
        int[][] result = new int[m][n];
        for (int i=0; i<m; i++) {
            result[i][0] = 1;
        }
        for (int i=0; i<n; i++) {
            result[0][i] = 1;
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                result[i][j] = result[i-1][j] + result[i][j-1];
            }
        }
        return result[m-1][n-1];
    }

    /* Leetcode 63. Unique Paths II
    with obstacles
    在上一题的基础上设置了一些障碍点，所以只需要对障碍点进行判断即可。遇到障碍点时方案数设置为0.
    */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] result = new int[m][n];
        
        if (obstacleGrid[0][0]==1) return 0;
        
        result[0][0]=1;
        for (int i=1; i<m; i++) {
            if (obstacleGrid[i][0]!=1) result[i][0] = result[i-1][0];
            else result[i][0] = 0;
        }
        for (int i=1; i<n; i++) {
            if (obstacleGrid[0][i]!=1) result[0][i] = result[0][i-1];
            else result[0][i] = 0;
        }
        
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                if (obstacleGrid[i][j]!=1) result[i][j] = result[i-1][j] + result[i][j-1];
                else result[i][j]=0;
            }
        }
        return result[m-1][n-1];
    }

    /* Leetcode 70. Climbing Stairs
    You are climbing a stair case. It takes n steps to reach to the top.
    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
    状态：f[i]走到i点的方案数
    转移方程：f[i] = f[i-1]+f[i-1]，因为只能走一步或者两步，所以只能从前一个或者两个格子过来。
    初始化：f[0] = 1;f[1] = 1;f[2] = 2
    结果：f[n]
    */
    public int climbStairs(int n) {
        if (n==1) return 1;
        if (n==2) return 2;
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(2);
        for (int i=2; i<n; i++) {
            result.add(result.get(i-1)+result.get(i-2));
        }
        return result.get(result.size()-1);
    }

    /* Leetcode 55. Jump Game
    Given an array of non-negative integers, you are initially positioned at the first index of the array.
    Each element in the array represents your maximum jump length at that position.
    Determine if you are able to reach the last index.
    */
    /* DP
    数组中的数字表示当前点能够跳跃的最大步长，判断是否可以跳到最后一步：是否有可行方案问题

    坐标型动态规划，一维坐标

    状态：s[i]，是否能从起点跳到i点

    取决于前面是否存在点j：
        从起点是否能跳到j点：s[j]
        从j是否能跳到i：j+s[j]>=i
    转移方程：s[i] = s[j] && j+s[j]>=i（j<i）

    初始化：s[0]=1

    答案：s[m]，最后一个元素的状态
    */
    public boolean canJump(int[] nums) {
        boolean[] result = new boolean[nums.length];
        result[0] = true;
        
        for (int i=1; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if (result[j] && nums[j]+j>=i) {
                    result[i] = true;
                    break;
                }
            }
        }
        return result[nums.length-1];
    }
    /* Greedy Algo
    两个指针，一个从头向尾移动，另一个计算能够到达的最远距离。
    需要注意的是：左侧指针向右移动时不能超过记录最远到达距离的指针。
    */
    public boolean canJump_Greedy(int[] nums) {
        if (nums.length==0) return false;
        
        int i=0;
        int farthest = nums[0];
        while (farthest<nums.length-1 && i<nums.length-1 && i<=farthest) {
            farthest = Math.max(farthest, i+nums[i]);
            i++;
        }
        return farthest>=nums.length-1;
    }


    /* Leetcode 45. Jump Game II
    Given an array of non-negative integers, you are initially positioned at the first index of the array.
    Each element in the array represents your maximum jump length at that position.
    Your goal is to reach the last index in the minimum number of jumps.
    */
    /* DP O(n^2): time limit exceed
    状态：s[i]从起点出发跳到i点需要步数
    转移方程：s[i] = min(s[j]+1),j满足条件可以一步跳到i，加个判断
    初始化：s[0] = 0
    答案：s[m]，最后一个元素状态
    */
    public int jump(int[] nums) {
        if (nums.length<=0) return 0;
        int[] minStep = new int[nums.length];
        
        minStep[0] = 0;
        for(int i =1; i < nums.length;i++){
            minStep[i] = Integer.MAX_VALUE;
        }
        for (int i=1; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if(j + nums[j] >= i) {
                    minStep[i] = Math.min(minStep[i],minStep[j]+1);
                }
            }
        }
        return minStep[nums.length-1];
    }

    /* Greedy Algo O(n)
    https://www.youtube.com/watch?time_continue=203&v=vBdo7wtwlXs&feature=emb_logo
    The main idea is based on greedy. 
    Let's say the range of the current jump is [curBegin, curEnd], 
    curFarthest is the farthest point that all points in [curBegin, curEnd] can reach. 
    Once the current point reaches curEnd, then trigger another jump, and set the new curEnd with curFarthest, then keep the above steps
    */
    public int jump_greedy(int[] nums) {
        int jumps = 0, curEnd = 0, curFarthest = 0;
        // The reason we used i < length-1 is because it excludes the last value in nums. 
        // We don't need to care about furthestJump we can get from the last element.
        for (int i=0; i<nums.length-1; i++) {
            curFarthest = Math.max(curFarthest, i+nums[i]);
            if (i==curEnd) {
                jumps++;
                curEnd = curFarthest;
            }
            if (curEnd==nums.length-1) break;
        }
        return jumps;
    }

    /* Leetcode 300. Longest Increasing Subsequence
    Given an unsorted array of integers, find the length of longest increasing subsequence.
    */
    /* DP O(n^2): https://www.youtube.com/watch?v=fV-TF4OvZpk
    状态：f[i]表示从任意一个木桩出发，从低到高，跳到i点，最多踩过多少个木桩
    转移方程：f[i] = max(f[j]+1) j满足：j<i && nums[j]<nums[i]
    初始化：f[0] = f[1] = …= f[n-1] = 1 从前面的任何一个点出发跳到此处要经过多少根木桩，初始化时，只经过自己跳到自己踩过的木桩树为1。
    结果：max(f[1],f[2],….,f[n-1]) 因为递增子序列不一定以最后一个元素为结尾，这道题要求的是最长的子序列，所以需要在所有的点里面找到最大的值返回。
    */
    public int lengthOfLIS(int[] nums) {
        if (nums.length<=0) return 0;
        int result = 1;
        
        int[] longest = new int[nums.length];
        for (int i=0; i<nums.length; i++) longest[i]=1;
        
        for (int i=0; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if (nums[i]>nums[j]) {
                    longest[i] = Math.max(longest[i], longest[j]+1);
                    result = Math.max(result, longest[i]);
                }
            }
        }
        return result;   
    }

    /* Binary search
    */
}
