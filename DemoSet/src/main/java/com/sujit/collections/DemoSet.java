package com.sujit.collections;

import java.util.LinkedList;

/**
 * A class to implement a basic version of set without using
 * any standard Hash Table libraries.
 *
 * Make it generic, and implement basic add, remove and check
 * methods.
 */
public class DemoSet<K> {
    private Bucket[] buckets;
    private int capacity;

    public DemoSet(){
        this.capacity = 128;
        buckets = new Bucket[capacity];
        for(int i = 0; i < capacity; i++){
            buckets[i] = new Bucket();
        }
    }

    private int getHash(K data){
        int hash = data!=null?data.hashCode():0;
        return Math.abs(hash) % capacity;
    }

    public void add(K data){
        int bucketNumber = getHash(data);
        buckets[bucketNumber].insert(data);
    }

    public void delete(K data){
        int bucketNumber = getHash(data);
        buckets[bucketNumber].remove(data);
    }

    public boolean isPresent(K data){
        int bucketNumber = getHash(data);
        return buckets[bucketNumber].checkExistence(data);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(Bucket b : buckets){
            if(!b.isEmptyBucket()){
                sb.append(b.getBucketValue()).append(", ");
            }
        }
        sb.delete(sb.length()-2,sb.length()).append("]");
        return sb.toString();
    }
}

class Bucket<K> {
    private LinkedList<K> container;

    public Bucket(){
        this.container = new LinkedList<>();
    }

    public void insert(K data){
        int position = this.container.indexOf(data);
        if(position == -1){
            this.container.addFirst(data);
        }
    }

    public void remove(K data){
        int position = this.container.indexOf(data);
        if(position!=-1){
            this.container.remove(position);
        }
    }

    public boolean checkExistence(K data){
        int position = this.container.indexOf(data);
        return (position!=-1);
    }

    public K getBucketValue(){
        return this.container.getFirst();
    }

    public boolean isEmptyBucket(){
        return this.container.isEmpty();
    }
}
