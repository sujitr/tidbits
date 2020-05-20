package com.sujit.exercise.PermutationInString;

public class WithArrays {
    public boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()){
            return false;
        }
        int[] s1map = new int[26];
        for(int i = 0; i < s1.length(); i++){
            s1map[s1.charAt(i)-'a']++;
        }
        for(int i = 0; i <= s2.length()-s1.length(); i++){
            /*
            create new set of array map for every
            s1 length equivalents in s2
             */
            int[] s2map = new int[26];
            for(int j = 0; j < s1.length(); j++){
                s2map[s2.charAt(i+j)-'a']++;
            }
            /*
            and then match that against the s1 map.
            if anyone matches then bingo.
             */
            if(match(s1map,s2map)) return true;
        }
        return false;
    }

    private boolean match(int[] a, int[] b){
        for(int i = 0; i < 26; i++){
            if(a[i]!=b[i]) return false;
        }
        return true;
    }

}
