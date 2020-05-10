package com.sujit.exercise.StraightLineCheck;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void checkStraightLine_1() {
        int[][] points = {{1,2},{2,3},{3,4},{4,5},{5,6},{6,7}};
        Solution solution = new Solution(points);
        Assert.assertTrue(solution.checkStraightLine());
    }

    @Test
    public void checkStraightLine_2() {
        int[][] points = {{1,1},{2,2},{3,4},{4,5},{5,6},{7,7}};
        Solution solution = new Solution(points);
        Assert.assertFalse(solution.checkStraightLine());
    }
}