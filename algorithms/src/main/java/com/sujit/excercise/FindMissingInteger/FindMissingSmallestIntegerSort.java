package com.sujit.excercise.FindMissingInteger;

import java.util.Arrays;

/**
 * A sort approach to find the smallest positive
 * integer missing from the unsorted array.
 */
public class FindMissingSmallestIntegerSort {
    private int[] data;

    public FindMissingSmallestIntegerSort(int[] data){
        this.data = data;
    }

    public int getSmallestInteger(){
        Arrays.sort(data);
        int checkInt = 1;
        for(int n : data){
            if(n > 0 && n == checkInt){
                checkInt++;
            }
        }
        return checkInt;
    }
}
