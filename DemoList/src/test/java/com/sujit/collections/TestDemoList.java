package com.sujit.collections;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDemoList {
	
	DemoList<Integer> myList = null;

	@Before
	public void setUpBefore() throws Exception {
		myList = new DemoList<Integer>();
	}

	@After
	public void tearDownAfter() throws Exception {
		myList = null;
	}

	@Test
	public void testDemoListSize() {
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);
		myList.add(5);
		assertTrue(myList.size()==5);
	}
	
	@Test
	public void testDemoListAdd() {
		assertTrue(myList.size()==0);
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);
		myList.add(5);
		assertTrue(myList.toString().equalsIgnoreCase("[1,2,3,4,5]"));
	}
	
	@Test
	public void testDemoListGet() {
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);
		myList.add(5);
		assertTrue(myList.get(2)==3);
	}
	
	@Test
	public void testDemoListRemove() {
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);
		myList.add(5);
		assertTrue(myList.remove(3)==4);
		assertTrue(myList.size()==4);
		assertTrue(myList.toString().equalsIgnoreCase("[1,2,3,5]"));
	}
	
	@Test
	public void testDemoListRemoveWithElements(){
		DemoList<String> dl = new DemoList<String>();
		dl.add("Manna");
        dl.add("Kathy");
        dl.add("Bocha");
        dl.add("Maria");
        dl.add("Jenna");
        dl.add("Avril");
        dl.add("Humphrey");
        assertTrue(dl.remove("Avril"));
        assertFalse(dl.remove("Aviici"));
        assertTrue(dl.size()==6);
        assertTrue(dl.toString().equalsIgnoreCase("[Manna,Kathy,Bocha,Maria,Jenna,Humphrey]"));
        dl = null;
	}

}
