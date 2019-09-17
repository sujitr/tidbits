package com.sujit.excercise.TwoNumber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a list of numbers and a number k, return whether any
 * two numbers from the list add up to k.
 *
 * For example, given [10, 15, 3, 7] and k of 17, return true
 * since 10 + 7 is 17.
 *
 * Bonus: Can you do this in one pass?
 */
public class TwoNumbers {

    private int[] data;
    int sum;

    public TwoNumbers(int[] data, int sum){
        this.data = data;
        this.sum = sum;
    }

    /**
     * approach with a Map
     * @return true if two such numbers exists in the
     * given array whose sum equals to given number
     */
    public boolean isPresentTwoSuchNumber_withMap(){
        boolean result = false;
        Map<Integer, Integer> checkMap = new HashMap<>();
        for(int n : data){
            int minuend = sum - n;
            // check if the map has the minuend of current number as value
            // against a key for current number
            if(checkMap.containsKey(n)){
                if(checkMap.get(n).equals(minuend)){
                    result = true;
                    break;
                }
            }else{
                checkMap.put(minuend,n);
            }
        }
        return result;
    }

    /**
     * approach with a Set
     * @return true if two such numbers exists in the
     * given array whose sum equals to given number
     */
    public boolean isPresentTwoSuchNumber_withSet(){
        boolean result = false;
        Set<Integer> checkSet = new HashSet<>();
        for(int n : data){
            int minuend = sum - n;
            if(checkSet.contains(n)){
                result = true;
            }else{
                checkSet.add(minuend);
            }
        }
        return result;
    }



    public static void main(String[] args){
        /*
        normally we can use two passes ((O) n square) to compare each
        number and checking if the array contains the minuend to add up to k.
        But, it can also be achieved in a single pass using a map or a set.
        */
        int[] data = {10, 15, 3, 7};
        int sum = 19;
        TwoNumbers twoNumbers = new TwoNumbers(data, sum);
        System.out.println(twoNumbers.isPresentTwoSuchNumber_withSet());
    }
}
