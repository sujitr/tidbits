package com.sujit.exercise.FindFirstUniqueCharacter;

public class Smart {
    private String data;

    public Smart(String data) {
        this.data = data;
    }

    public int getFirstUniqChar(){
        int res = Integer.MAX_VALUE;

        for(char c = 'a'; c <= 'z'; c++){
            int index = data.indexOf(c);

            if(index != -1 && index == data.lastIndexOf(c))
                res = Math.min(res, index);
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
