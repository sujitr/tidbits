package com.sujit.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the length of the largest palindrome possible
 * from the given string.
 *
 *  when it comes to palindromes, only thing to check is for pairs.
 *  with a pair of same characters you can create a palindrome. Single
 *  characters also count, BUT only one of them can be used to form
 *  a palindrome only in the middle.
 *
 *  so, ideally you should be thinking like how many pairs of characters
 *  are there, and then is there are any odd characters, out of which only
 *  one can be used (just about any one of them).
 */
public class LargestPalindromeLength {

    public static void main(String[] args){
        LargestPalindromeLength largestPalindromeLength = new LargestPalindromeLength();
        String test = "abccccdd";
        int longest = largestPalindromeLength.findLongestPalindrome(test);
        System.out.println("Length of longest palindrome for '"+test+"' is :"+longest);

    }

    public int findLongestPalindrome(String s) {
        // first create the hashmap storing pair information
        Map<Character, Integer> charMap = new HashMap<>();
        for(char c : s.toCharArray()){
            if(charMap.containsKey(c)){
                charMap.put(c, charMap.get(c)+1);
            }else{
                charMap.put(c, 1);
            }
        }

        //count pairs from the hashmap
        boolean hasOddChar = false;
        int numOfPairs = 0;
        for(Character c : charMap.keySet()){
            int count = charMap.get(c);
            int pairCount = count/2;
            if(!hasOddChar && count - pairCount*2 >=1){
                // means this char is an extra odd char
                hasOddChar = true;
            }
            numOfPairs += pairCount;
        }
        return numOfPairs*2 + (hasOddChar?1:0);
    }

}
