package com.sujit.exercise;

import java.util.Arrays;

/**
 * There exists two sorted arrays. First array has enough
 * capacity to store the second one. We need an algorithm
 * which would merge second one into first one in a sorted
 * manner.
 */
public class ArrayMergeWithSort {
    public static void main(String[] args){
        int[] partA = new int[8];
        int[] partB = {2,6,9,17,45};

        partA[0] = 1;
        partA[1] = 4;
        partA[2] = 7;

        merge(partA, partB, 3, 5);
        System.out.println(Arrays.toString(partA));
    }

    public static void merge(int[] partA, int[] partB, int a, int b){
        int k = (a+b)-1;
        int p = a-1;
        int q = b-1;
        // assumption here is array B has greater number of elements than A
        while(p>=0 && q>=0){
            if(partA[p] > partB[q]){
                partA[k--] = partA[p--];
            }else{
                partA[k--] = partB[q--];
            }
        }
        // put the remaining elements of B into A
        while(q>=0){
            partA[k--] = partB[q--];
        }
    }

}
