package com.sujit.memoizer;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

/**
 * This is a fourth approach towards caching with a concurrent HashMap.
 * Here we try to avoid a very narrow possibility of two threads trying to check the 
 * cache for same key at the same time (well, almost at the same time).<br>
 * With some unlucky timing it might (just might) happen that both the threads see
 * that cache does not contain any future with given key, as none had the time to 
 * put a future back yet. <br>
 * This could happen because the normal cache put operation is not atomic. So by the 
 * time one thread is putting the future in, if another thread comes to get it, it might
 * happen that it would not see that future, and start the expensive operation again. <br>
 * So to avoid this possibility we have to use the putIfAbsent of concurrentmap
 * which is a atomic check-if-absent-then-set method. <br>
 * Also, one more improvement is needed which is cleaning cache pollution when any submitted
 * computation is cancelled or failed. If we dont clean that future from cache then any
 * future attempt to get it will retrieve only cancellation. 
 */

public class Memoizer4<A,V> implements Computable<A,V> {
    private final ConcurrentMap<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
    private final Computable<A,V> c;
    
    public Memoizer4(Computable<A,V> c){
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
           cache.putIfAbsent(arg, futureValue);
           computationFutureTask.run();
       }
       try{
           return futureValue.get();
       }catch(CancellationException ce){
           cache.remove(arg,futureValue);
           return null; // is this a valid return in this scenario?? Am not sure
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