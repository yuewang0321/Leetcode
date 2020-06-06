class Solution {
    /* Leetcode 121. Best Time to Buy and Sell Stock
    Say you have an array for which the ith element is the price of a given stock on day i.
    If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), 
    design an algorithm to find the maximum profit.
    Note that you cannot sell a stock before you buy one.
    只能买一次找最大profit
    分析：
    第i天卖出所获的最大利润为：prices[i] - min(prices[0]~prices[i-1])
    步骤：从前向后遍历，更新到当前天的价格最低值，更新到当前天的利润最大值
    */
    public int maxProfit(int[] prices) {
        if (prices.length<=0) return 0;
        
        int maxProfit = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        
        for (int i=0; i<prices.length; i++) {
            min = Math.min(min, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i]-min);
        }       
        return maxProfit;
    }
    
    /* Leetcode 122. Best Time to Buy and Sell Stock II
    Say you have an array prices for which the ith element is the price of a given stock on day i.
    Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
    (i.e., buy one and sell one share of the stock multiple times).
    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
    能买卖多次，找最大profit，先买后卖
    计算每一天跟前一天的价格差，将价格差大于0 的利润累加，就是获得的最大利润
    */
    public int maxProfit2(int[] prices) {
        if (prices.length<=0) return 0;
        
        int profit = 0;
        for (int i=1; i<prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        return profit;
    }

}