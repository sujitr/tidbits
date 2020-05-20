package com.sujit.exercise.FindTownJudge;

public class Solution {
    public int findJudge(int N, int[][] trust) {
        if(trust.length==0){
            return N==1? 1: -1;
        }
        int targetTrustCount = N-1;
        int[] people = new int[N];
        for(int i = 0; i < trust.length; i++){
            people[trust[i][0]-1]--;
            people[trust[i][1]-1]++;
        }
        for(int i = 0; i < people.length; i++){
            if(people[i] == targetTrustCount){
                return i+1;
            }
        }
        return -1;
    }
}
