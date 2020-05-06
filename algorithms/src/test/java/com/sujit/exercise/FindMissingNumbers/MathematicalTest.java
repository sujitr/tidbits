package com.sujit.exercise.FindMissingNumbers;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MathematicalTest {

    @Test
    public void findMissingNumbers_1() {
        int[] data = {4,3,2,7,8,2,3,1};
        Mathematical mathematical = new Mathematical(data);
        List<Integer> expected = Arrays.asList(5,6);
        Assert.assertEquals(expected, mathematical.findMissingNumbers());
    }

    @Test
    public void findMissingNumbers_2() {
        int[] data = {1,1};
        Mathematical mathematical = new Mathematical(data);
        List<Integer> expected = Arrays.asList(2);
        Assert.assertEquals(expected, mathematical.findMissingNumbers());
    }

}