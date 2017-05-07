package com.sujit.memoizer;

import java.math.BigInteger;

public class ExpensiveCalculation implements Computable<String, BigInteger> {
    public BigInteger compute(String arg){
        // after lot of computation
        return new BigInteger(arg);
    }
}