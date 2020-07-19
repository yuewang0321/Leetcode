class Solution {
    /* Leetcode 139. Word Break
    Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
    determine if s can be segmented into a space-separated sequence of one or more dictionary words.
    Note:
        The same word in the dictionary may be reused multiple times in the segmentation.
        You may assume the dictionary does not contain duplicate words.
    
    状态：memory[i]表示前i个字母是否可以被切分
    */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> hash = new HashSet<String>(wordDict);
        Map<String, Boolean> memory = new HashMap<String, Boolean>();
        return wordBreak(s, hash, memory);
    }
    private boolean wordBreak(String s, Set<String> wordDict, Map<String, Boolean> memory) {
        if (memory.containsKey(s)) return memory.get(s);
        
        if (wordDict.contains(s)) {
            memory.put(s, true);
            return true;
        }
        
        for (int i=0; i<s.length(); i++) {
            if (wordDict.contains(s.substring(i)) && wordBreak(s.substring(0, i), wordDict, memory)) {
                memory.put(s, true);
                return true;
            }
        }
        memory.put(s, false);
        return false;
    }

    /* Leetcode 322. Coin Change
    You are given coins of different denominations and a total amount of money amount. 
    Write a function to compute the fewest number of coins that you need to make up that amount. 
    If that amount of money cannot be made up by any combination of the coins, return -1.
    Input: coins = [1, 2, 5], amount = 11
    Output: 3 
    Explanation: 11 = 5 + 5 + 1
    https://www.youtube.com/watch?v=jgiZlGzXMBw&feature=emb_title

    dp[i]代表达到目前amount需要的最小coin个数
    dp[i] = Math.min(dp[i-c]+1, dp[i]), where c is nums in coin
    */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for (int i=1; i<=amount; i++) {
            for (int c: coins) {
                if (c<=i) {
                    dp[i] = Math.min(dp[i-c]+1, dp[i]);
                }
            }
        }
        return (dp[amount]==amount+1 ? -1 : dp[amount]);
    }

    /* Leetcode 983. Minimum Cost For Tickets
    In a country popular for train travel, you have planned some train travelling one year in advance.  The days of the year that you will travel is given as an array days.  Each day is an integer from 1 to 365.

    Train tickets are sold in 3 different ways:
        a 1-day pass is sold for costs[0] dollars;
        a 7-day pass is sold for costs[1] dollars;
        a 30-day pass is sold for costs[2] dollars.
    The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on day 2, then we can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.

    Return the minimum number of dollars you need to travel every day in the given list of days.

    Intuition
    For each travel day, we can buy a one-day ticket, 
    or use 7-day or 30-day pass as if we would have purchased it 7 or 30 days ago. 
    We need to track rolling costs for at least 30 days back, and use them to pick the cheapest option for the next travel day.

    We track the minimum cost for all calendar days in dp. 
    For non-travel days, the cost stays the same as for the previous day. 
    For travel days, it's a minimum of yesterday's cost plus single-day ticket, or cost for 8 days ago plus 7-day pass, 
    or cost 31 days ago plus 30-day pass.
    */
    public int mincostTickets(int[] days, int[] costs) {
        int[] pass = new int[] {1,7,30};
        int[] dp = new int[days[days.length-1]+1];
        
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i=1; i<dp.length; i++) {
            boolean found = check(days, i);
            if (found) {
                for (int c=0; c<pass.length; c++) {
                    int back = Math.max(i-pass[c], 0);
                    dp[i] = Math.min(dp[back]+costs[c], dp[i]);
                }
            } 
            else {
                dp[i] = dp[i-1];
            }
        }
        return (dp[dp.length-1]==Integer.MAX_VALUE ? -1 : dp[dp.length-1]);
    }
    
    public boolean check(int[] days, int check) {
        for (int i=0; i<days.length; i++) {
            if (days[i] == check) return true;
        }
        return false;
    }

    /* Leetcode 518. Coin Change 2
    You are given coins of different denominations and a total amount of money. Write a function to compute the number 
    of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.

    Permuatation: use coin change's idea
    Combination: iterate through coin first, then add previous to avoid same elements
    */
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int c : coins) {
            for (int i=1; i<dp.length; i++) {
                if (c<=i) {
                    dp[i] += dp[i-c];
                }
            }
        }
        return dp[dp.length-1];
    }
}
