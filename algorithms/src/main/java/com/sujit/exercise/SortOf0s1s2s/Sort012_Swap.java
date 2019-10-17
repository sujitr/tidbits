package com.sujit.exercise.SortOf0s1s2s;

import java.util.Arrays;

/**
 * Algorithm to sort an array consisting of only 0's, 1's and 2's.
 *
 * Time Complexity: O(n)
 *
 * This approach performs unnecessary swaps for some inputs
 * which are not really required. We can modify the swap function
 * to do a check that the values being swapped are same or not,
 * if not same, then only swap the values.
 */
public class Sort012_Swap {
    private int[] data;

    public Sort012_Swap(int[] data){
        this.data = data;
    }

    public void sort(){
        int l = 0;
        int mid = 0;
        int r = data.length-1;

        while(mid <= r){
            switch (data[mid]){
                case 0: {
                    int temp = data[l];
                    data[l] = data[mid];
                    data[mid] = temp;

                    l++;
                    mid++;
                    break;
                }
                case 1: {
                    mid++;
                    break;
                }
                case 2: {
                    int temp = data[r];
                    data[r] = data[mid];
                    data[mid] = temp;

                    r--;
                    break;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] data = {1,2,0,1,2,0,0,1,2,2,0,1};
        Sort012_Swap sort012Swap = new Sort012_Swap(data);
        sort012Swap.sort();
        System.out.println(Arrays.toString(data));
    }
}
