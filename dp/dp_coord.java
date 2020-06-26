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
        System.out.println(Arrays.deepToString(result));
        for (int i=0; i<n; i++) {
            result[0][i] = 1;
        }
        System.out.println(Arrays.deepToString(result));
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                result[i][j] = result[i-1][j] + result[i][j-1];
            }
        }
        return result[m-1][n-1];
    }
}
