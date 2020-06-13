package com.sujit.exercise.DecodeString;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void test1() {
        Solution solution = new Solution();
        Assert.assertEquals("aaabcbc",solution.decodeString("3[a]2[bc]"));
    }

    @Test
    public void test2() {
        Solution solution = new Solution();
        Assert.assertEquals("accaccacc",solution.decodeString("3[a2[c]]"));
    }

    @Test
    public void test3() {
        Solution solution = new Solution();
        Assert.assertEquals("abcabccdcdcdef",solution.decodeString("2[abc]3[cd]ef"));
    }

    @Test
    public void test4() {
        Solution solution = new Solution();
        Assert.assertEquals("zzzyypqjkjkefjkjkefjkjkefjkjkefyypqjkjkefjkjkefjkjkefjkjkefef",solution.decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
    }



}