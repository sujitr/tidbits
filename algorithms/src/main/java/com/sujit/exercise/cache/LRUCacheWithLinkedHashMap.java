package com.sujit.exercise.cache;

import java.util.LinkedHashMap;

public class LRUCacheWithLinkedHashMap extends LinkedHashMap<Integer, String> {
    private int cacheSize; // max size of the cache

    /*
     creating an LRU cache based on access order
     eviction policy for freshness, using
     underlying LinkedHashMap
      */
    public LRUCacheWithLinkedHashMap(int size){
        super(size, 0.75f, true);
        this.cacheSize = size;
    }

    /**
     * remove the oldest element when size limit is reached
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<Integer,String> eldest){
        return size() > cacheSize;
    }
}
