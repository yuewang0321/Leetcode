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
}
