package com.sujit.exercise.MajorityElement;

/**
 * IMPORTANT: This algorithm is only applicable
 * here as its given that the data will have
 * one clear majority element
 */
public class MooresVotingAlgo {
    private int[] data;

    public MooresVotingAlgo(int[] data) {
        this.data = data;
    }

    public int getMajorityElement(){
        int count = 0, result = 0;
        for(int n : data){
            if(count == 0){
                result = n;
            }
            if(n!=result){
                count--;
            }else{
                count++;
            }
        }
        return result;
    }
}
