package com.sujit.exercise.RemoveKDigits;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BruteForceTest {

    @Test
    public void removeKdigits_1() {
        BruteForce bruteForce = new BruteForce("1432219");
        Assert.assertEquals("1219",bruteForce.removeKdigits(3));
    }

    @Test
    public void removeKdigits_2() {
        BruteForce bruteForce = new BruteForce("10200");
        Assert.assertEquals("200",bruteForce.removeKdigits(1));
    }

    @Test
    public void removeKdigits_3() {
        BruteForce bruteForce = new BruteForce("10");
        Assert.assertEquals("0",bruteForce.removeKdigits(2));
    }
}