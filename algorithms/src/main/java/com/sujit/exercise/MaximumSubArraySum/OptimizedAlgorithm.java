package com.sujit.exercise.MaximumSubArraySum;

public class OptimizedAlgorithm {
    int[] nums;

    public OptimizedAlgorithm(int[] nums) {
        this.nums = nums;
    }

    public int getMaxSubArraySum() {
        int sum=0;
        int largest =Integer.MIN_VALUE;
        for(int num:nums){
            sum+=num;
            largest = Math.max(sum,largest);
            if(sum<0)
                sum=0;
        }
        return largest==Integer.MIN_VALUE?-1:largest;
    }
}
