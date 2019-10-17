package com.sujit.exercise.FindMissingInteger;

/**
 * A naive approach to find the smallest positive
 * integer missing from the unsorted array.
 */
public class FindMissingSmallestIntegerNaive {
    private int[] data;

    public FindMissingSmallestIntegerNaive(int[] data){
        this.data = data;
    }

    public int getSmallestInteger(){
        int stepInteger = 1;
        for(int m : data){
            if(isPresentInArray(stepInteger)){
                stepInteger++;
            }else{
                break;
            }
        }
        return stepInteger;
    }

    private boolean isPresentInArray(int x){
        boolean result = false;
        for(int n : data){
            if(n == x){
                result = true;
            }
        }
        return result;
    }
}
