package com.sujit.exercise.RansomNote;

import java.util.HashMap;
import java.util.Map;

public class BruteForce {

    private String ransomNote, magazine;

    public BruteForce(String ransomNote, String magazine) {
        this.ransomNote = ransomNote;
        this.magazine = magazine;
    }

    public boolean canConstruct() {
        Map<Character, Integer> charCount = new HashMap<>();
        for(Character c : magazine.toCharArray()){
            charCount.put(c, charCount.getOrDefault(c,0)+1);
        }
        for(Character c : ransomNote.toCharArray()){
            if(charCount.containsKey(c)){
                int count = charCount.get(c)-1;
                if(count == 0){
                    charCount.remove(c);
                }else{
                    charCount.put(c, count);
                }
            }else{
                return false;
            }
        }
        return true;
    }
}
