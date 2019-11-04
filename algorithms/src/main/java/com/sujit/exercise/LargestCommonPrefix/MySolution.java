package com.sujit.exercise.LargestCommonPrefix;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 *
 * Input: ["dog","racecar","car"]
 * Output: ""
 *
 * Input: []
 * Output: ""
 */
public class MySolution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        int numOfStrings = strs.length;
        char matchChar = '-';
        int i = 0, j = 0;
        while(numOfStrings > 0){
            char c = getCharacterAtIndex(strs[i],j);
            if(c == '-'){
                break;
            }
            if(matchChar == '-'){
                matchChar = c;
            }else{
                if(c != matchChar){
                    break;
                }
            }
            if(i < numOfStrings-1){
                i++;
                continue;
            }
            if(i == numOfStrings-1){
                sb.append(matchChar);
                i = 0;
                j++;
                matchChar='-';
            }
        }
        return sb.toString();
    }

    private char getCharacterAtIndex(String s, int n){
        if(s.length()-1 >= n){
            return s.charAt(n);
        }else{
            return '-';
        }
    }
}
