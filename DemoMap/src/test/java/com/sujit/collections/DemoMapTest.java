package com.sujit.collections;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Class to test the demo map.
 */
public class DemoMapTest {
    @Test
    public void testMapGet(){
        DemoMap<Integer, Integer> dMap = new DemoMap<Integer, Integer>();
        dMap.put(3, 67);
        dMap.put(12, 4561);
        dMap.put(34, 56);
        dMap.put(34, 56);
        dMap.put(78, 4178);
        assertTrue(dMap.get(12) == 4561);
    }
    
    @Test
    public void testMapSize(){
        DemoMap<Integer, Integer> dMap = new DemoMap<Integer, Integer>();
        dMap.put(3, 67);
        dMap.put(12, 4561);
        dMap.put(34, 56);
        dMap.put(34, 56);
        dMap.put(78, 4178);
        assertTrue(dMap.size() == 4);
        dMap.remove(34);
        assertTrue(dMap.size() == 3);
    }
    
    @Test
    public void testKeyPresence(){
        DemoMap<Integer, Integer> dMap = new DemoMap<Integer, Integer>();
        dMap.put(3, 67);
        dMap.put(12, 4561);
        dMap.put(34, 56);
        dMap.put(34, 56);
        dMap.put(78, 4178);
        dMap.put(null, 2178);
        assertTrue(dMap.containsKey(12));
        assertFalse(dMap.containsKey(23));
        assertTrue(dMap.containsKey(null));
    }
    
    @Test
    public void testValuePresence() {
        DemoMap<Integer, Integer> dMap = new DemoMap<Integer, Integer>();
        dMap.put(3, 67);
        dMap.put(12, 4561);
        dMap.put(34, 56);
        dMap.put(34, 56);
        dMap.put(78, 4178);
        dMap.put(null, 2178);
        assertTrue(dMap.containsValue(2178));
        assertFalse(dMap.containsValue(45));
    }
    
}