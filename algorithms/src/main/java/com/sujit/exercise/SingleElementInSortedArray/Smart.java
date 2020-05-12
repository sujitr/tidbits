package com.sujit.exercise.SingleElementInSortedArray;

public class Smart {
    private int[] nums;

    public Smart(int[] nums) {
        this.nums = nums;
    }

    public int singleNonDuplicate() {
        int lo = 0, len = nums.length, hi = len / 2;

        while (lo < hi){
            int mid = lo + ((hi - lo)/2);
            if (nums[2*mid] == nums[2*mid+1]){
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return nums[2 * lo];
    }
}
