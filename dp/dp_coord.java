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
    // 或者从上往下计算也可以
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

    
}
