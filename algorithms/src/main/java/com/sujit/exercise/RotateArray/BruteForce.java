package com.sujit.exercise.RotateArray;

import java.util.Arrays;

public class BruteForce {
    public static void main(String[] args){
        int[] data = {1,2,3,4,5,6};
        int k = 9;
        k = k%data.length; // this takes care of cases where k > array length
        while (k > 0) {
            rotateOnceRight(data);
            k--;
        }
        System.out.println(Arrays.toString(data));
    }

    private static void rotateOnceRight(int[] data) {
        int temp = data[data.length-1];
        for(int i = data.length-1; i > 0; i--){
            data[i] = data[i-1];
        }
        data[0] = temp;
    }
}
