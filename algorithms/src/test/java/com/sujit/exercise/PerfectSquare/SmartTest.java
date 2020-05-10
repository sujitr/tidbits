package com.sujit.exercise.PerfectSquare;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SmartTest {

    @Test
    public void isPerfectSquare_1() {
        Smart smart = new Smart(16);
        Assert.assertTrue(smart.isPerfectSquare());
    }

    @Test
    public void isPerfectSquare_2() {
        Smart smart = new Smart(5);
        Assert.assertFalse(smart.isPerfectSquare());
    }

    @Test
    public void isPerfectSquare_3() {
        Smart smart = new Smart(14);
        Assert.assertFalse(smart.isPerfectSquare());
    }

}