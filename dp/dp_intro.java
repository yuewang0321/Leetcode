class Solution {
    /* 方法1 从上往下深度优先遍历搜索，记录所有情况之中最小值 
    time-limit exceeded 
    time complexity: O(2^n)
    */
    public int best = Integer.MAX_VALUE;
    public int minimumTotal(List<List<Integer>> triangle) {
        dfs(triangle, 0, 0, 0);
        return best;
    }
    public void dfs(List<List<Integer>> triangle, int i, int j, int sum) {
        int height = triangle.size();
        if (i==height) {
            best = Math.min(best, sum);
            return;
        }
        dfs(triangle, i+1, j, sum+triangle.get(i).get(j));
        dfs(triangle, i+1, j+1, sum+triangle.get(i).get(j));
    }

    /* 方法2
    从最底层走到某个点[i,j]的最短路径长度可以分解为两个子问题：
        1. 这个点自身的路径 长度+下一层左边节点路径长度
        2. 这个点自身的路径 长度+下一层左右边节点路径长度
    最终结果取两种情况最小值
    边界条件：最下面一层路径最小长度是1
    time-limit exceeded 
    time complexity: O(2^n)
    */
    public int minimumTotal2(List<List<Integer>> triangle) {
        return divide(triangle, 0, 0);
    }
    public int divide(List<List<Integer>> triangle, int i, int j) {
        int height = triangle.size();
        if (i==height) return 0;
        return triangle.get(i).get(j) + 
            Math.min(divide(triangle, i+1, j), divide(triangle, i+1, j+1));
    }
    
    /* 显然上面的方法有很多重复计算的值：
        [x,y]
        ↓    ↘
    [x+1,y] [x+1,y+1]
    ↓      ↘ ↓       ↘
    [x+2,y] [x+2,y+1] [x+2,y+2]
    这样[x+2, y+1]就被计算了两次
    */

    /* 方法3: 记忆化搜索 
    如果把计算的值存储下来，后面再用到就可以避免重复计算了
    从下往上算，最短路径还是取决于[i+1][j]和[i+1][j+1]
    这样只需要一直更新最短路径，从上往下的话要保留所有之前的result
    https://www.youtube.com/watch?v=p1LlPZYw42g
    */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size()==0) return 0;
        int rows = triangle.size();
        int[] dp = new int[rows+1];
        
        for (int i=rows-1; i>=0; i--) {
            for (int j=0; j<=i; j++) {
                dp[j] = Math.min(dp[j], dp[j+1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];    
    }



    /* 总结

    什么时候用DP
        三个条件满足其中一个则极有可能需要用DP求解：
            1. 求最大，最小值
            2. 判断是否可行
            3. 统计方案个数
    什么时候不用DP
        三个条件满足其中一个则极有可能不用DP
            1. 输出所有方案
            2. 给的是集合，不是序列（元素顺序不可换）
            3. 暴力算法的时间复杂度已经是多项式复杂度了（n^2,n^3）,dp擅长将exponential complexity优化到polynominal complexity

    动态规划四要素：
    1. 状态 - f[][]的含义   最难
    2. 方程 - 状态之间的联系，怎么用小状态计算大状态
    3. 初始化 - 最小状态是什么，起点
    4. 答案 - 最大状态是什么，终点

    两种方法
    1. 从下往上：从起点到终点
    2. 从上向下：从终点出发，反推至起点
    */
}
