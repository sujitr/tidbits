package com.sujit.excercise.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private Map<Integer, Node> cache;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int maxCapacity){
        this.capacity = maxCapacity;
        cache = new HashMap<>();
    }

    public void put(int key, int value){
        if(cache.containsKey(key)){
            Node existingNode = cache.get(key);
            removeNode(existingNode);
            existingNode.setData(value);
            addNode(existingNode);
        }else{
            if(cache.size()>=capacity) { // need to evict some stale stuff at head
                Node headNode = cache.get(head.getKey());
                removeNode(headNode);
            }
            Node newNode = new Node(key, value);
            addNode(newNode);
            cache.put(key, newNode);
        }
    }

    public int get(int key){
        if(cache.containsKey(key)){
            Node node = cache.get(key);
            removeNode(node);
            addNode(node);
            return node.getData();
        }else{
            return -1;
        }
    }

    /**
     * Method to remove a Node from anywhere on the doubly linked list
     * @param node
     */
    private void removeNode(Node node){
        if(node.getPrevious()!=null){   // node is somewhere in middle
            node.getPrevious().setNext(node.getNext());
        }else{ // node is at the beginning
            head = node.getNext();
            node.setPrevious(null);
        }

        if(node.getNext()!=null){
            node.getNext().setPrevious(node.getPrevious());
        }else{
            tail = node.getPrevious();
            node.setNext(null);
        }
    }

    /**
     * Method to add a Node to the end of the doubly linked list
     */
    private void addNode(Node node){
        if(tail!=null){
            tail.setNext(node);
        }
        node.setPrevious(tail);
        node.setNext(null);
        tail = node;

        if(head==null){
            head = tail;
        }
    }


}

class Node {
    private int key;
    private int data;
    private Node previous;
    private Node next;

    public Node(int key, int data) {
        this.key = key;
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
