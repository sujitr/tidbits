package com.sujit.collections;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Class to test the demo map.
 * 
 * Remember: JUnit always creates one instance of the test class for each @Test method. 
 * This is a fundamental design decision to make it easier to write tests without side-effects. 
 * Good tests do not have any order-of-run dependencies and creating fresh instances of the test 
 * class and its instance variables for each test is crucial in achieving this.
 */
public class DemoMapTest {
    
    DemoMap<Integer, Integer> dMap;
    
    @Before
    public void setUp(){
        dMap = new DemoMap<Integer, Integer>();
        dMap.put(3, 67);
        dMap.put(12, 4561);
        dMap.put(34, 56);
        dMap.put(34, 56);
        dMap.put(78, 4178);
        dMap.put(null, 2178);
    }
    
    @After
    public void tearDown(){
        dMap = null;
    }
    
    @Test
    public void testMapGet(){
        assertTrue(dMap.get(12) == 4561);
    }
    
    @Test
    public void testMapSize(){
        assertTrue(dMap.size() == 5);
        dMap.remove(34);
        assertTrue(dMap.size() == 4);
    }
    
    @Test
    public void testKeyPresence(){
        assertTrue(dMap.containsKey(12));
        assertFalse(dMap.containsKey(23));
        assertTrue(dMap.containsKey(null));
    }
    
    @Test
    public void testValuePresence() {
        assertTrue(dMap.containsValue(2178));
        assertFalse(dMap.containsValue(45));
    }
    
}