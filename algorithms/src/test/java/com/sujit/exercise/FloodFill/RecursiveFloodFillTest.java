package com.sujit.exercise.FloodFill;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecursiveFloodFillTest {

    @Test
    public void changeColor() {
        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        RecursiveFloodFill recursiveFloodFill = new RecursiveFloodFill(image);
        recursiveFloodFill.changeColor(1,1,2);
        int[][] expectedImage = {{2,2,2},{2,2,0},{2,0,1}};
        Assert.assertArrayEquals(expectedImage, image);
    }
}