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
}
