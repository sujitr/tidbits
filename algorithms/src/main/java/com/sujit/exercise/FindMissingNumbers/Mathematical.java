package com.sujit.exercise.FindMissingNumbers;

import java.util.ArrayList;
import java.util.List;

public class Mathematical {
    private int[] nums;

    public Mathematical(int[] data) {
        this.nums = data;
    }

    public List<Integer> findMissingNumbers() {
        int min = nums[0];
        int max = nums[0];
        List<Integer> result = new ArrayList<>();
        /*
        first find out the min and max range of the given array
         */
        for(int i : nums){
            if(i < min) min = i;
            if(i > max) max = i;
        }
        /*
        Then create another array within the range
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
