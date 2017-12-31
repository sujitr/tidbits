package com.sujit.collections;

public class DemoMap<K,V> {
    // first define a static inner class which will hold the map entries in the linkedlist
    static class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> nextEntry;
        
        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.nextEntry = next;
        }
    }
    private Entry<K,V>[] mapArray;
    private int capacity = 10;
    
    public DemoMap(){
        mapArray = new Entry[capacity];
    }
    
    public void put(K key, V value) {
        
    }
    
    public V get(K key){
        
    }
    
    public V remove(K key) {
        
    }
    
    public void display(){
        
    }
}