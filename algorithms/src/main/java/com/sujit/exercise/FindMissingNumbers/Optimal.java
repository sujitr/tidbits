package com.sujit.exercise.FindMissingNumbers;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution of the problem without
 * using extra space.
 *
 * The problem specifies that the numbers
 * in the array will be in the range [1, n]
 * where n is the number of elements in the array.
 * Can we use this information and modify the array
 * in-place somehow to find what we need?
 */
public class Optimal {
    private int[] nums;

    public Optimal(int[] nums) {
        this.nums = nums;
    }

    public List<Integer> findMissingNumbers(){
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < nums.length; i++){
            int val = Math.abs(nums[i])-1;
            if(nums[val]>0){
                nums[val] = -nums[val];
            }
        }

        for(int i = 0; i < nums.length; i++){
            if(nums[i] > 0){
                result.add(i+1); // pay attention, adding 1 to 'i'
            }
        }
        return result;
    }
}
