package com.sujit.exercise.FindMissingNumbers;

import java.util.ArrayList;
import java.util.List;

public class Mathematical {
    private int[] nums;

    public Mathematical(int[] data) {
        this.nums = data;
    }

    public List<Integer> findMissingNumbers() {
        /*
        Since the missing numbers will always be in
        the range of 1 to array length
         */
        int min = 1;
        int max = nums.length;
        List<Integer> result = new ArrayList<>();
        /*
        Create another array within the range
         */
        int[] sample = new int[max-min+1];
        /*
        keep a count of the numbers
         */
        for(int n : nums){
            sample[n-min]++;
        }
        /*
        find the missing ones
         */
        for(int i = 0; i < sample.length; i++){
            if(sample[i]==0){
                result.add(i+min);
            }
        }
        return result;
    }
}
