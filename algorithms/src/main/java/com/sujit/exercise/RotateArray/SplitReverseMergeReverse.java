package com.sujit.exercise.RotateArray;

import java.util.Arrays;

public class SplitReverseMergeReverse {
    public static void main(String[] args){
        int[] data = {1,2,3,4,5,6};
        int k = 3;
        k = k%data.length; // this takes care of cases where k > array length
        rotateArray(data, k);
        System.out.println(Arrays.toString(data));
    }

    private static void rotateArray(int[] data, int k) {
        int len = data.length;
        int[] sub1 = new int[k];
        int[] sub2 = new int[len-k];
        int[] temp = new int[len];
        for(int i = 0; i < k; i++){
            sub1[i] = data[i];
        }
        for(int i = k; i < len; i++){
            sub2[i-k] = data[i];
        }
        reverse(sub1);
        reverse(sub2);
        System.arraycopy(sub1, 0, temp, 0,k);
        System.arraycopy(sub2, 0, temp, k,len-k);
        reverse(temp);
        for(int i = 0; i < len; i++){
            data[i] = temp[i];
        }
    }

    private static void reverse(int[] data){
        int len = data.length-1;
        for(int i = 0; i <= len/2; i++){
            int temp = data[i];
            data[i] = data[len-i];
            data[len-i] = temp;
        }
    }
}
