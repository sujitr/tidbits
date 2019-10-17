package com.sujit.exercise;

import java.util.Arrays;

/**
 * Given an array of integers, return a new array such that each
 * element at index i of the new array is the product of all the
 * numbers in the original array except the one at i.
 *
 * For example, if our input was [1, 2, 3, 4, 5], the expected
 * output would be [120, 60, 40, 30, 24]. If our input
 * was [3, 2, 1], the expected output would be [2, 3, 6].
 *
 * Follow-up: what if you can't use division?
 */
public class ProductArray {
    public static void main(String[] args){
        /* input data */
        int[] data = {1,2,3,4,5};
        /* array to hold product of all elements left of any index */
        int[] lprod = new int[data.length];
        /* array to hold product of all elements right of any index */
        int[] rprod = new int[data.length];
        /* array to hold final product by multiplying both above arrays */
        int[] result = new int[data.length];

        /*
        initializing left array's leftmost and right arrays rightmost element
        as 1. That's because 1 would be the starting number for all products.
         */
        lprod[0] = rprod[data.length-1] = 1;

        // populate the left product array
        for(int i = 1; i < data.length; i++){
            lprod[i] = lprod[i-1]*data[i-1];
        }
        System.out.println("Left product array : "+Arrays.toString(lprod));

        // populate the right product array
        for(int j = data.length-2; j>=0;j--){
            rprod[j] = rprod[j+1]*data[j+1];
        }
        System.out.println("Right product array : "+Arrays.toString(rprod));

        // multiplying both of them to get desired result array
        for(int i = 0; i < data.length; i++){
            result[i] = lprod[i]*rprod[i];
        }
        System.out.println("Final product array : "+Arrays.toString(result));
    }
}
