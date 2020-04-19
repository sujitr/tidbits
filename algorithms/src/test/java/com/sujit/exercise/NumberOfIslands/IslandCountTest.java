package com.sujit.exercise.NumberOfIslands;

import org.junit.Assert;
import org.junit.Test;

public class IslandCountTest {
    @Test
    public void testIslandCount_1(){
        char[][] data = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
        CountIslands countIslands = new CountIslands(data);
        Assert.assertEquals(1, countIslands.getCount());
    }

    @Test
    public void testIslandCount_2(){
        char[][] data = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        CountIslands countIslands = new CountIslands(data);
        Assert.assertEquals(3, countIslands.getCount());
    }
}
