package com.sujit.sort;

import java.util.Arrays;

public class CountingSort {
    private int[] data;

    public CountingSort(int[] data){
        this.data = data;
    }

    public void performSort(){
        /*
        first get the range of the numbers in given array
         */
        int min = Arrays.stream(data).min().getAsInt();
        int max = Arrays.stream(data).max().getAsInt();
        int range = max-min;
        /*
        initialize the counter array to keep counts of all
        numbers in the given array
         */
        int[] counter = new int[range+1];
        /*
         initialize result array which would hold numbers
         in correct sorted position
         */
        int[] result = new int[data.length];
        /*
        find the count of the numbers
         */
        for(int i : data){
            counter[i-min]++;
        }
        int count = 0;
        for(int i = min; i <= max; i++ ){
            while(counter[i-min]!=0){
                result[count++]=i;
                counter[i-min]--;
            }
        }
        System.out.println(Arrays.toString(result));
    }

    public static void main(String[] args){
        int[] data = {-8,-5,3,-5,3,-8,3};
        CountingSort countingSort = new CountingSort(data);
        countingSort.performSort();
    }
}
