package com.sujit.collections;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class DemoSetTest {
    @Test
    public void testSet_1(){
        DemoSet<String> set = new DemoSet<>();
        set.add("Ruby");
        set.add("Brook");
        set.add("Carrie");
        set.add("Brook");
        set.delete("Carrie");
        set.delete("Carrie");
        set.add("Brook");
        assertEquals("[Brook, Ruby]",set.toString());
    }

    @Test
    public void testSet_2(){
        DemoSet<String> set = new DemoSet<>();
        set.add("Ruby");
        set.add("Brook");
        set.add("Carrie");
        set.add("Brook");
        set.delete("Carrie");
        assertFalse(set.isPresent("Carrie"));
        assertTrue(set.isPresent("Ruby"));
    }
}
