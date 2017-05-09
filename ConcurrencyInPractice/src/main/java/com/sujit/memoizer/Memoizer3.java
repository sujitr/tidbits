package com.sujit.memoizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * This is a third approach towards caching with a concurrent HashMap.
 * Here we try to check if a current computation is already underway with 
 * same key. <br>
 * The way we do it is by using a FutureTask wrapped around the computable object.
 * We store the FutureTask object in the cache so that any other thread can query 
 * and understand that the computation is already underway and so its better to 
 * wait for that to get over and get the result from that rather than initiating 
 * a separate computation.
 * 
 */

public class Memoizer3<A,V> implements Computable<A,V> {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
    private final Computable<A,V> c;
    
    public Memoizer3(Computable<A,V> c){
        this.c = c;
    }
    
    public V compute(final A arg) throws InterruptedException {
       Future<V> futureValue = cache.get(arg);
       if(futureValue == null){
           Callable<V> computationCall = new Callable<V>(){
             public V call() throws InterruptedException {
                 return c.compute(arg);
             }  
           };
           FutureTask<V> computationFutureTask = new FutureTask<V>(computationCall);
           futureValue = computationFutureTask;
           cache.put(arg, futureValue);
           computationFutureTask.run();
       }
       try{
           return futureValue.get();
       }catch(ExecutionException e){
           throw launderThrowable(e.getCause());
       }
    }
    
    public static RuntimeException launderThrowable(Throwable t){
        if(t instanceof RuntimeException)
            return (RuntimeException) t;
        else if(t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("This is not a checked exception", t);
    }
    
}