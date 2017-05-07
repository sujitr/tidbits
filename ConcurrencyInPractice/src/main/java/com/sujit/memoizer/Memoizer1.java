package com.sujit.memoizer;

import java.util.Map;
import java.util.HashMap;
import net.jcip.annotations.GuardedBy;

/**
 * This is a basic approach towards caching with a simple HashMap.
 * Since HashMap is not thread safe hence the whole compute method
 * has been put under a synchronized lock with current object. <br>
 * This ensures thread safety but poses big scalability problem.
 */

public class Memoizer1<A,V> implements Computable<A,V> {
    @GuardedBy("this")
    private final Map<A,V> cache = new HashMap<A,V>();
    private final Computable<A,V> c;
    
    public Memoizer1(Computable<A,V> c){
        this.c = c;
    }
    
    public synchronized V compute(A arg) throws InterruptedException {
       V result = cache.get(arg);
       if(result == null) {
           result = c.compute(arg);
           cache.put(arg,result);
       }
       return result;
    }
    
}