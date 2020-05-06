package com.sujit.exercise.FindFirstUniqueCharacter;

public class UsingArray {
    private String data;

    public UsingArray(String data) {
        this.data = data;
    }

    public int getFirstUniqChar(){
        int result = -1;
        int[] charCounts = new int[26];
        for(int i = 0; i < data.length(); i++){
            char c = data.charAt(i);
            charCounts[c-'a']++;
        }
        for(int i = 0; i < data.length(); i++){
            if(charCounts[data.charAt(i)-'a']==1){
                result = i;
                break;
            }
        }
        return result;
    }
}
