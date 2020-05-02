package com.sujit.exercise.FindFirstUnique;

import org.junit.Assert;
import org.junit.Test;

public class FirstUniqueTest {
    @Test
    public void testLRUApproach(){
        int[] data = {2,3,5};
        FirstUniqueLRU firstUniqueLRU = new FirstUniqueLRU(data);
        Assert.assertEquals(2, firstUniqueLRU.showFirstUnique());
        firstUniqueLRU.add(5);
        Assert.assertEquals(2, firstUniqueLRU.showFirstUnique());
        firstUniqueLRU.add(2);
        Assert.assertEquals(3, firstUniqueLRU.showFirstUnique());
        firstUniqueLRU.add(3);
        Assert.assertEquals(-1, firstUniqueLRU.showFirstUnique());
    }

    @Test
    public void testLinkedHashMapApproach(){
        int[] data = {2,3,5};
        FirstUniqueLinkedHashMap fulhmap = new FirstUniqueLinkedHashMap(data);
        Assert.assertEquals(2, fulhmap.showFirstUnique());
        fulhmap.add(5);
        Assert.assertEquals(2, fulhmap.showFirstUnique());
        fulhmap.add(2);
        Assert.assertEquals(3, fulhmap.showFirstUnique());
        fulhmap.add(3);
        Assert.assertEquals(-1, fulhmap.showFirstUnique());
    }
}
