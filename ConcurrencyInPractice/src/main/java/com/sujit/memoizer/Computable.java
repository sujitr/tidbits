package com.sujit.memoizer;

/**
 * Computable interface describes a function which takes an input
 * of type A and computes and returns a result of type V.
 * This interface represents the main functionaluty of the computation
 * which needs to be cached.
 */

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}