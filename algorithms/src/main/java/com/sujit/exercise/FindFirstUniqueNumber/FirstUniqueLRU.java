package com.sujit.exercise.FindFirstUniqueNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an LRU Cache based approach.
 * Map keeps track of the entries in O(1),
 * and a doubly linked list keeps the
 * elements in needed order (first unique
 * at the tail position) to access the
 * first unique in O(1) time.
 */
public class FirstUniqueLRU {
    private int[] data;
    private Map<Integer, Node> map;
    private Node head;
    private Node tail; // will keep track of the first unique number

    public FirstUniqueLRU(int[] data){
        this.data = data;
        map = new HashMap<>();
        for(int num : data){
            add(num);
        }
    }

    public void add(int num){
        if(map.get(num)!=null){
            Node existingNode = map.get(num);
            // just remove this entry
            removeNode(existingNode);
            map.remove(num);
        }else{
            Node newNode = new Node(num, num);
            addNodeAtHead(newNode);
            map.put(num,newNode);
        }
    }

    public int showFirstUnique(){
        int result = -1;
        Node n = tail;
        if(n!=null){
            result = n.getValue();
        }
        return result;
    }

    private void removeNode(Node existingNode) {
        if(existingNode.getPrevious()!=null){ // middle node
            existingNode.getPrevious().setNext(existingNode.getNext());
        }else{ // node at head
            head = existingNode.getNext();
            existingNode.setPrevious(null);
        }

        if(existingNode.getNext()!=null){
            existingNode.getNext().setPrevious(existingNode.getPrevious());
        }else{ // node at tail
            tail = existingNode.getPrevious();
            existingNode.setNext(null);
        }
    }

    private void addNodeAtHead(Node node){
        if(head!=null){
            node.setNext(head);
            node.setPrevious(null);
            head.setPrevious(node);
            head = node;
        }else{
            head = node;
        }
        if(tail == null){
            tail = head;
        }
    }

    public void displayList(){
        Node n = head;
        while(n!=null){
            System.out.print(n.getValue()+"->");
            n = n.getNext();
        }
        System.out.println();
    }


}

class Node {
    private int key;
    private int value;
    private Node next;
    private Node previous;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}
