package com.sujit.memoizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.GuardedBy;

/**
 * This is a second approach towards caching with a concurrent HashMap.
 * Since HashMap is not thread safe and putting the entire caching under
 * synchronization is a big scalability risk, we are changing it to a 
 * concurrent hashmap. <br>
 * This ensures thread safety with less contention and slightly improved
 * scalability. But it can still have issues in the long run.
 */

public class Memoizer2<A,V> implements Computable<A,V> {
    @GuardedBy("this")
    private final Map<A,V> cache = new ConcurrentHashMap<A,V>();
    private final Computable<A,V> c;
    
    public Memoizer2(Computable<A,V> c){
        this.c = c;
    }
    
    public V compute(A arg) throws InterruptedException {
       V result = cache.get(arg);
       if(result == null) {
           result = c.compute(arg);
           cache.put(arg,result);
       }
       return result;
    }
    
}