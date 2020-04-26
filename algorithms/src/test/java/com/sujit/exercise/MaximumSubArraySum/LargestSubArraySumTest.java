package com.sujit.exercise.MaximumSubArraySum;

import org.junit.Assert;
import org.junit.Test;

public class LargestSubArraySumTest {
    @Test
    public void testKadanesAlgoApproach_1(){
        int[] data = {-2,1,-3,4,-1,2,1,-5,4};
        KadanesAlgorithm ka = new KadanesAlgorithm(data);
        Assert.assertEquals(6, ka.getMaxSubArraySum());
    }

    @Test
    public void testKadanesAlgoApproach_2(){
        int[] data = {-2,-1};
        KadanesAlgorithm ka = new KadanesAlgorithm(data);
        Assert.assertEquals(-1, ka.getMaxSubArraySum());
    }

    @Test
    public void testOptimizedApproach_1(){
        int[] data = {-2,1,-3,4,-1,2,1,-5,4};
        OptimizedAlgorithm oa = new OptimizedAlgorithm(data);
        Assert.assertEquals(6, oa.getMaxSubArraySum());
    }

    @Test
    public void testOptimizedApproach_2(){
        int[] data = {-2,-1};
        OptimizedAlgorithm oa = new OptimizedAlgorithm(data);
        Assert.assertEquals(-1, oa.getMaxSubArraySum());
    }

}
