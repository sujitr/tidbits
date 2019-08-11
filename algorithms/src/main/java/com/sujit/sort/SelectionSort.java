package com.sujit.sort;

import java.util.Arrays;

public class SelectionSort {
    private int[] data;

    public SelectionSort(int[] data){
        this.data = data;
    }

    public void performSort(){
        for(int i = 0; i < data.length; i++){
            for(int j = i+1; j < data.length; j++){
                if(data[i]>data[j]){
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] testData = {4,7,1,9,20,3,8};
        SelectionSort selectionSort = new SelectionSort(testData);
        selectionSort.performSort();
        System.out.println(Arrays.toString(testData));
    }
}
