package com.sujit.sort;

import java.util.Arrays;

/**
 * QuickSort implementation.
 * Pivot element selected to be the first element.
 *
 * QuickSort has two steps in the algo -
 * 1. Partition - Choose a pivot element and move elements smaller
 *                than pivot element to left, and elements larger
 *                than pivot element to right, of the pivot elements target
 *                correct position.
 *                After that place the pivot element to that target correct
 *                position.
 *
 * 2. Recursion - After first step, we would have two sub sets adjacent
 *                to both side of the actual pivot element in middle.
 *                So we call the partition step again for both of them
 *                recursively.
 */
public class QuickSort {
    private int[] data;

    public QuickSort(int[] data){
        this.data = data;
    }

    public void performSort(){
        sort(data, 0, data.length-1);
    }

    private void sort(int[] data, int left, int right){
        if(left < right){
            int splitPoint = partition(data, left, right);
            sort(data, left, splitPoint-1);
            sort(data, splitPoint+1,right);
        }
    }

    /**
     * Method to get the partition index, and do the partition
     * based on the chosen pivot position.
     * @param data
     * @param left
     * @param right
     * @return
     */
    private int partition(int[] data, int left, int right) {
        int pivotIndex = left; // taking leftmost element as pivot element
        int pivotValue = data[left];
        left++; // moving left pointer to next element to start comparing
        while(true){
            /*
            keep moving left pointer forward till you encounter an element
            larger than pivot and till left and right pointers crosses.
            If they cross that means all the elements have been compared.
             */
            while(data[left] < pivotValue && left<=right) left++;
            /*
            keep moving right pointer backward till you encounter an
            element smaller than pivot and till left and right pointers crosses.
            If they cross that means all the elements have been compared.
             */
            while(data[right] > pivotValue && left<=right) right--;
            /*
            if left and right pointers have crossed, all elements have been
            compared, so now we just need to place the pivot element in
            its correct sorted position. Else, we need to swap places of the
            elements at left and right positions so that they are on the
            correct side of the pivot element.
             */
            if(left > right){
                break;
            }else{
                swap(data, left, right);
            }
        }
        /*
        place the pivot element in it's correct sorted position
         */
        swap(data,pivotIndex, right);
        return right;
    }

    private void swap(int[] data, int left, int right){
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

    public static void main(String[] args) {
        int[] data = {12,5,34,1,76,23};
        QuickSort qs = new QuickSort(data);
        qs.performSort();
        System.out.println("Sorted :: "+ Arrays.toString(data));
    }
}
