package com.sujit.exercise.CustomATOI;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CustomATOITest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testPositive(){
        CustomATOI customATOI = new CustomATOI("-453");
        assertEquals(-453,customATOI.getConvertedInteger());
    }

    @Test
    public void testNegative(){
        CustomATOI customATOI = new CustomATOI("  ");
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Improper input to the function");
        customATOI.getConvertedInteger();
    }
}
