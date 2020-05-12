package com.sujit.exercise.SingleElementInSortedArray;

public class BruteForce {
    private int[] nums;

    public BruteForce(int[] nums) {
        this.nums = nums;
    }

    public int singleNonDuplicate() {
        int result = 0;
        for(int i = 0; i <= nums.length-1;){
            if(i < nums.length-1 && nums[i]==nums[i+1]){
                i = i+2;
            }else{
                result = nums[i];break;
            }
        }

        return result;
    }
}
