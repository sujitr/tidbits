package com.sujit.exercise.FindMissingNumbers;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CounterBasedTest {

    @Test
    public void findMissingNumbers_1() {
        int[] data = {4,3,2,7,8,2,3,1};
        CounterBased counterBased = new CounterBased(data);
        List<Integer> expected = Arrays.asList(5,6);
        Assert.assertEquals(expected, counterBased.findMissingNumbers());
    }

    @Test
    public void findMissingNumbers_2() {
        int[] data = {1,1};
        CounterBased counterBased = new CounterBased(data);
        List<Integer> expected = Arrays.asList(2);
        Assert.assertEquals(expected, counterBased.findMissingNumbers());
    }

}