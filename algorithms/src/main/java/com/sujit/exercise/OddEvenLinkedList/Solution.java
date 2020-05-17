package com.sujit.exercise.OddEvenLinkedList;

public class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode oddNode = head, evenNode = head.next, evenHead = evenNode;
        while (evenNode != null && evenNode.next != null) {
            oddNode.next = evenNode.next;
            oddNode = oddNode.next;
            evenNode.next = oddNode.next;
            evenNode = evenNode.next;
        }
        oddNode.next = evenHead;
        return head;
    }
}
