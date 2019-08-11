package com.sujit.sort;

import java.util.Arrays;

public class MergeSort {
    private int[] data;

    public MergeSort(int[] data) {
        this.data = data;
    }

    public void performSort(){
        sort(data, 0, data.length-1);
    }

    private void sort(int[] data, int left, int right){
        if(right > left){
            int midPoint = (left+right)/2;
            /*
            keep dividing the array until the left side is just one element
             */
            sort(data, left, midPoint);
            /*
            keep dividing the array until the right side is just one element
             */
            sort(data,midPoint+1, right);
            /*
            start merging from bottom up, with single element arrays
             */
            merge(data, left, midPoint, right);
        }
    }

    private void merge(int[] data, int left, int mid, int right) {
        System.out.println("merging... :Left="+left+", Right="+right+", Mid="+mid);
        /*
        First, create a temp target array which would hold final sorted result, and
        whose total length is equal to the range given to this 'merge' method
        call
         */
        int targetArrayLength = (right-left)+1;
        int[] targetArray = new int[targetArrayLength];
        /*
        Second create two pointers which will track the elements from the starting
        position of both sub-arrays. 'p' for left part and 'q' for right part.
         */
        int p=left, q=mid+1;
        /*
        Third, create another pointer which will help in moving elements to
        target array as per their correct sorted position, starting from 0
         */
        int k = 0;
        /*
        now loop through the original array for the given range, and
        (a) check if starting position in the left array has already moved past the
        end of the left side array.
        (b) check if the starting position in the right array has already
        moved past the end of right side array.
        (c) if, both above checks fail, then the trackers are within boundaries,
        hence the compare the digits at the respective tracker positions in both sides
        and move them to target array as per their sorted order.
        REMEMBER: to keep incrementing the tracker positions as you take any step.
         */
        for(int i = left; i <= right; i++){
            if (p > mid){
                /*
                left array tracker has already moved past its end, so now
                just copy all remaining elements of right array as it is to
                target array.
                 */
                targetArray[k++] = data[q++];
            } else if (q > right){
                /*
                right array tracker has already moved past its end, so now
                just copy all remaining elements of the left array as it is to
                target array.
                 */
                targetArray[k++] = data[p++];
            } else if(data[p] > data[q]) {
                targetArray[k++] = data[q++];
            } else {
                targetArray[k++] = data[p++];
            }
        }
        /*
        Now, just loop through the targetArray and copy the elements (which are
        already in sorted order) to the original array. You can take the 'left'
        pointer in the original array as tracker for that, as after this operation
        there is no need for it. And remember to loop till k-1 as k has already
        jumped one index ahead with last operation on targetArray.
         */
        for(int i = 0; i < k; i++){
            data[left++] = targetArray[i];
        }
    }

    public static void main(String[] args) {
        int[] testData = {4,7,1,9,20,3,8};
        MergeSort ms = new MergeSort(testData);
        ms.performSort();
        System.out.println(Arrays.toString(testData));
    }

}
