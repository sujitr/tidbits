package com.sujit.exercise.SortedRotatedArray;

/**
 * Find index of an element in a sorted but rotated array.
 * return -1 if that element is not available.
 *
 * Assume that all the elements in the array are unique.
 *
 * This is a dual inline binary search approach. Instead of finding
 * the pivot element first and then searching in respective
 * sub-array's, in this approach we use a one time binary search
 * approach for attempting both the goals above in shot.
 *
 * 1) Find middle point mid = (l + h)/2
 * 2) If key is present at middle point, return mid.
 * 3) Else If arr[l..mid] is sorted
 *     a) If key to be searched lies in range from arr[l]
 *        to arr[mid], recur for arr[l..mid].
 *     b) Else recur for arr[mid+1..h]
 *
 * 4) Else (arr[mid+1..h] must be sorted)
 *     a) If key to be searched lies in range from arr[mid+1]
 *        to arr[h], recur for arr[mid+1..h].
 *     b) Else recur for arr[l..mid]
 */
public class DualInlineBinarySearchMethod {

    private int[] data;

    public DualInlineBinarySearchMethod(int[] data){
        this.data = data;
    }

    public int searchNumber(int n){
        return performSearch(n, 0, data.length-1);
    }

    private int performSearch(int x, int l, int r){
        if(l>r){
            return -1;
        }
        int mid = (l+r)/2;
        if(data[mid] == x){
            return mid;
        }
        if(data[l] < data[mid]){
            if(data[l] <=x && x <=data[mid]){
                return performSearch(x, l, mid);
            }else{
                return performSearch(x, mid+1, r);
            }
        }else{
            if(data[mid+1] <=x && x<=data[r]){
                return performSearch(x, mid+1, r);
            }else{
                return performSearch(x, l, mid);
            }
        }
    }


    public static void main(String[] args){
        int[] data = {7,8,9,1,2,3,4,5,6};
        /*
        The approach is somewhat like binary search.
        You start with finding the middle element of the array.
        If this middle element is what you are searching is then
        all is well and good. But if not then keep going.
        So you end up with two sub-arrays. One of this would be
        sorted, you find that out by comparing the edge elements
        in both of them.
        Now, if the item to be searched seems to be between the edges
        of this sorted array then you recur the binary search.
        Otherwise, you still recur the other half.
         */
        DualInlineBinarySearchMethod basicSearch = new DualInlineBinarySearchMethod(data);
        System.out.println(basicSearch.searchNumber(6));
    }
}
