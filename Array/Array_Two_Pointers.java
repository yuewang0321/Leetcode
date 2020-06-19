class Solution {
    /* Leetcode 3. Longest Substring Without Repeating Characters
    Given a string, find the length of the longest substring without repeating characters.
    用hash存储出现过的character,iterate through s, 如果遇到相同的就从hash中移除，否则加到hash中，update max
    */
    public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int end = 0;
        int max = 0;
        HashSet<Character> hash = new HashSet();
        
        while (end < s.length()) {
            if (!hash.contains(s.charAt(end))) {
                hash.add(s.charAt(end));
                end++;
                max = Math.max(max, hash.size());
            }
            else {
                hash.remove(s.charAt(start));
                start++;
            }
        }
        return max;
    }

    /* Leetcode 28. Implement strStr()
    Implement strStr().
    Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
    两个pointer用sliding window比较每个substring
    */
    public int strStr(String haystack, String needle) {
        if (needle.length()==0) return 0;
        if (haystack.length()==0) return -1;

        int start = 0, end=needle.length()-1;
        while (end<haystack.length()) {
            if (haystack.substring(start, end+1).equals(needle)) {
                return start;
            }
            start++;
            end++;
        }
        return -1;
        
    }

    /* Leetcode 345. Reverse Vowels of a String
    Write a function that takes a string as input and reverse only the vowels of a string.
    两指针一前一后向中间遍历，遇到元音对换即可。
    这里需要注意的是java的String是不可更改的，需要用StringBuilder复制一份再做修改：result.setCharAt(i,s.charAt(j));
    */
    public String reverseVowels(String s) {
        StringBuilder result = new StringBuilder(s);
        int start = 0, end = s.length()-1;
        while (start<end) {
            while (start<end && !isVowel(s.charAt(start))) start++;
            while (start<end && !isVowel(s.charAt(end))) end--;
            char temp = s.charAt(start);
            result.setCharAt(start,s.charAt(end));
            result.setCharAt(end,temp);
            start++;
            end--;
        }
        return result.toString();
    }
    public boolean isVowel(char ch){
        if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'){
            return true;
        }
        else return false;
    }

    /* Leetcode 125. Valid Palindrome
    Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
    需要一个确定是否是有效字符的函数isvalid,利用java中的Character.isLetter()和Character.isDigit()
    两指针一前一后遍历，遇到无效字符跳过，比较两指针指向的字符是否相等，如果不相等直接返回false
    */
    public boolean isPalindrome(String s) {
        int start = 0, end = s.length()-1;
        while (start<end) {
            while (start<end && !isvalid(s.charAt(start))) start++;
            while (start<end && !isvalid(s.charAt(end))) end--;

            if (Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end))) return false;
            start++;
            end--;
        }
        return true;         
    }
    private boolean isvalid (char c) {
      return Character.isLetter(c) || Character.isDigit(c);
    }

    /* Leetcode 680. Valid Palindrome II
    Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
    Follow normal way (two pointers) to check if s is palindrome. When two chars are not equal, 
    try to skip (pseudo delete) either left one or right one and continue checking.
    */
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length()-1;
        while (i<j && s.charAt(i)==s.charAt(j)){
            i++;
            j--;
        }
        
        if (i>=j) return true;
        else {
            return (checkisPalidrome(s, i+1, j) || checkisPalidrome(s, i, j-1));
        }
    }   
    public boolean checkisPalidrome(String s, int i, int j) {
        while (i<j) {
            if (s.charAt(i)!=s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    /* Leetcode 524. Longest Word in Dictionary through Deleting
    Given a string and a string dictionary, find the longest string in the dictionary that can be formed by 
    deleting some characters of the given string. If there are more than one possible results, 
    return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.
    两种方法
    根据词的长度sort dictionary里的word，再去iterate through dictionary and check if word can be formed by given string
    或者不用sort, 每次更新最大值
    */
    public String findLongestWord(String s, List<String> d) {
        Collections.sort(d, (a,b) -> a.length() != b.length() ? -Integer.compare(a.length(), b.length()) :  a.compareTo(b));
        
        for (String word: d) {
            for (String dictWord : d) {
                int i = 0;
                for (char c : s.toCharArray()) 
                    if (i < dictWord.length() && c == dictWord.charAt(i)) i++;
                if (i == dictWord.length()) return dictWord;
            }
        }
        return "";
    }
    //or without sorting the dictonary
    public String findLongestWord(String s, List<String> d) {
        String longest = "";
        
        for (String word: d) {
            int i=0;
            for (char c: s.toCharArray()) {
                if (i<word.length() && c==word.charAt(i)) i++;
            }
            if (i>=word.length()) {
                if ((word.length()>longest.length()) || (word.length()==longest.length() && word.compareTo(longest)<0)) {
                    longest = word;
                }
            }
        }
        return longest;
    }

    /* Leetcode 567. Permutation in String
    Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
    In other words, one of the first string's permutations is the substring of the second string.
    滑动窗口法，窗口长度设置为s1的长度，在s2中寻找s1的排列子串
    窗口向右滑动，直到窗口内出现的字母以及每个字母出现的次数和s1中一样，此时找到了满足条件的子数组。
    将s1中出现的字母和每个字母出现的次数记录在一个list中，因为只有26个字母，可以用一个长度为26的数组记录每个字母出现的次数
    */
    public boolean checkInclusion(String s1, String s2) {
        int[] s1map = new int[26];
        for (int i=0; i<s1.length(); i++) s1map[s1.charAt(i)-'a']++;
        
        int start = 0, end=s1.length()-1;
        while(end<s2.length()) {
            int[] s2map = new int[26];
            for (int i=start; i<=end; i++) {
                s2map[s2.charAt(i)-'a']++;
            }
            if (isSame(s1map, s2map)) return true;
            start++;
            end++;
        }
        return false;
    }
    public boolean isSame(int[] s1map,int[] s2map){
        for(int i = 0 ; i < 26;i++){
            if(s1map[i] != s2map[i]){
              return false;
            }
        }
        return true;
    }
    //here is a faster solution with same idea, 每次更新一下s2map，每次只更新两位
    public boolean checkInclusion_faster(String s1, String s2) {
        if(s1.length() > s2.length()){
            return false;
          }
          int[] s1map = new int[26];
          int[] s2map = new int[26];
          for(int i = 0 ; i < s1.length();i++){
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
          }
          for(int i = 0 ; i < s2.length() - s1.length();i++){
            if(isSame(s1map,s2map)){
              return true;
            }
            s2map[s2.charAt(s1.length()+i) - 'a']++;
            s2map[s2.charAt(i) - 'a']--;
          }
          return isSame(s1map,s2map);
    }

    /* Leetcode 713. Subarray Product Less Than K
    Your are given an array of positive integers nums.
    Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than k.
    滑窗+两指针i,j题:
    如果窗口内乘积>=k，i++,窗口缩小
    如果滑窗内乘积<k，窗口内包含的以j为结束的子数组个数为j-i+1
    j-i+1: i到j是最大的subarray，中间每一个都算上所以有j-i个，再加上j本身自己的subarray所以是j-i+1
    */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k<=1) return 0;
        
        int count = 0;
        int prod = 1;
        int left=0, right=0;
        
        while (right<nums.length) {
            prod *= nums[right];
            while (prod>=k) {
                prod /= nums[left];
                left++;
            }
            count += right - left + 1;
            right++;
        }
        return count;   
    }
}