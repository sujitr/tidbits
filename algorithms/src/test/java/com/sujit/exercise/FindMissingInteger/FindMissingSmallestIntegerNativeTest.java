package com.sujit.exercise.FindMissingInteger;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindMissingSmallestIntegerNativeTest {
    @Test
    public void testResultA(){
        int[] data = {3, 4, -1, 1};
        FindMissingSmallestIntegerNaive engine = new FindMissingSmallestIntegerNaive(data);
        assertEquals(2, engine.getSmallestInteger());
    }

    @Test
    public void testResultB(){
        int[] data = {1, 2, 0};
        FindMissingSmallestIntegerNaive engine = new FindMissingSmallestIntegerNaive(data);
        assertEquals(3, engine.getSmallestInteger());
    }

    @Test
    public void testResultC(){
        int[] data = {1, 1, 0, -1, -2};
        FindMissingSmallestIntegerNaive engine = new FindMissingSmallestIntegerNaive(data);
        assertEquals(2, engine.getSmallestInteger());
    }

    @Test
    public void testResultD(){
        int[] data = {2, 3, -7, 6, 8, 1, -10, 15};
        FindMissingSmallestIntegerNaive engine = new FindMissingSmallestIntegerNaive(data);
        assertEquals(4, engine.getSmallestInteger());
    }
}
