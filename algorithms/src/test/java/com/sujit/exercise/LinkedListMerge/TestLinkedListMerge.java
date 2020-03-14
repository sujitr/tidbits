package com.sujit.exercise.LinkedListMerge;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestLinkedListMerge {
    @Test
    public void test1(){
        MyLinkedListMerge myLinkedListMerge = new MyLinkedListMerge();

        ListNode listA = myLinkedListMerge.addNode(null,1);
        listA = myLinkedListMerge.addNode(listA,3);
        listA = myLinkedListMerge.addNode(listA,5);
        listA = myLinkedListMerge.addNode(listA,7);

        ListNode listB = myLinkedListMerge.addNode(null,1);
        listB = myLinkedListMerge.addNode(listB,2);
        listB = myLinkedListMerge.addNode(listB,4);
        listB = myLinkedListMerge.addNode(listB,6);

        ListNode merged = myLinkedListMerge.doMerge(listA, listB);
        assertEquals("[1,1,2,3,4,5,6,7]",myLinkedListMerge.getFormattedList(merged));
    }

    @Test
    public void test2(){
        MyLinkedListMerge myLinkedListMerge = new MyLinkedListMerge();

        ListNode listA = null;

        ListNode listB = myLinkedListMerge.addNode(null,1);
        listB = myLinkedListMerge.addNode(listB,2);
        listB = myLinkedListMerge.addNode(listB,4);
        listB = myLinkedListMerge.addNode(listB,6);

        ListNode merged = myLinkedListMerge.doMerge(listA, listB);
        assertEquals("[1,2,4,6]",myLinkedListMerge.getFormattedList(merged));
    }
}
