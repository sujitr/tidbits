package com.sujit.sort;

import java.util.Arrays;

public class BubbleSort {
    private int[] data;

    public BubbleSort(int[] data){
        this.data = data;
    }

    public void performSort(){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data.length-1; j++){
                if(data[j] > data[j+1]){
                    int temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] testData = {4,7,1,9,20,3,8};
        BubbleSort bs = new BubbleSort(testData);
        bs.performSort();
        System.out.println(Arrays.toString(testData));
    }
}
