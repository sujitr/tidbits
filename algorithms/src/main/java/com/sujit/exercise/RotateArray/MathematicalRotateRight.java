package com.sujit.exercise.RotateArray;

import java.util.Arrays;

/**
 * Mathematically the k'th rotated position to the right
 * of any element which is currently at position i is
 * = (i+k)%len, where len is the length of the array
 */
public class MathematicalRotateRight {
    public static void main(String[] args){
        int[] data = {1,2,3,4,5,6};
        int k = 2;
        rotateRight(data, k);
        System.out.println(Arrays.toString(data));
    }

    public static void rotateRight(int[] data, int k){
        int[] temp = new int[data.length];

        for(int i = 0; i < data.length; i++){
            temp[(i+k)%data.length] = data[i];
        }

        for(int i = 0; i < data.length; i++){
            data[i] = temp[i];
        }
    }
}
