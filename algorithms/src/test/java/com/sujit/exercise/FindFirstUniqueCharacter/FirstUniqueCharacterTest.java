package com.sujit.exercise.FindFirstUniqueCharacter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FirstUniqueCharacterTest {

    @Test
    public void testBruteForce(){
        BruteForce bruteForce = new BruteForce("loveleetcode");
        assertEquals(2, bruteForce.getFirstUniqChar());
    }

    @Test
    public void testSmart(){
        Smart smart = new Smart("leetcode");
        assertEquals(0, smart.getFirstUniqChar());
    }

    @Test
    public void testUsingArray(){
        BruteForce bruteForce = new BruteForce("lovelove");
        assertEquals(-1, bruteForce.getFirstUniqChar());
    }


}