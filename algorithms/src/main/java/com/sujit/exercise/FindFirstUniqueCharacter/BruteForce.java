package com.sujit.exercise.FindFirstUniqueCharacter;

public class BruteForce {
    private String data;

    public BruteForce(String data) {
        this.data = data;
    }

    public int getFirstUniqChar(){
        int result = -1;
        for(char c : data.toCharArray()){
            int index = data.indexOf(c);
            if(data.indexOf(c, index+1)==-1){
                result = index;
                break;
            }
        }
        return result;
    }
}
