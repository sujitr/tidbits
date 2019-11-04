package com.sujit.exercise.LargestCommonPrefix;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestMySolution {
    @Test
    public void testSolution1(){
        MySolution mySolution = new MySolution();
        String[] input = {"flower","flow","flight"};
        assertTrue(mySolution.longestCommonPrefix(input).equals("fl"));
    }

    @Test
    public void testSolution2(){
        MySolution mySolution = new MySolution();
        String[] input = {"dog","racecar","car"};
        assertTrue(mySolution.longestCommonPrefix(input).equals(""));
    }

    @Test
    public void testSolution3(){
        MySolution mySolution = new MySolution();
        String[] input = {};
        assertTrue(mySolution.longestCommonPrefix(input).equals(""));
    }

}
