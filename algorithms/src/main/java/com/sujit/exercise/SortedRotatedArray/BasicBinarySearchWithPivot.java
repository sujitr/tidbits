package com.sujit.exercise.SortedRotatedArray;

/**
 * Find index of an element in a sorted but rotated array.
 * return -1 if that element is not available.
 *
 * Assume that all the elements in the array are unique.
 *
 * This is a naive approach towards the problem. The core of
 * the approach is to find the pivot element with binary search
 * technique. The pivot element is the smallest element in the
 * array.
 * Once the pivot element is found, we generally have two parts
 * of the array, each one sorted. Now, if the element to be
 * searched is greater than pivot element and less than right most
 * element then it has to be in the right part of pivot. Otherwise
 * it has to be in left part of pivot. We can again use binary search
 * to find that element in the respective sub-array.
 *
 * Time Complexity O(logn)
 */
public class BasicBinarySearchWithPivot {

    private int[] data;

    public BasicBinarySearchWithPivot(int[] data){
        this.data = data;
    }

    public int searchNumber(int n){
        return performSearch(n, 0, data.length-1);
    }

    /**
     * Get the pivot position of the rotated sorted array. The pivot position is the
     * index of the smallest number in the array.
     * @return
     */
    public int getPivotPosition(){
        return findPivotIndex(0, data.length-1);
    }

    /**
     * Method to find a number in a sorted rotated array. First, the pivot element is
     * obtained and then normal binary search is called.
     * @param x - integer number which needs to be searched
     * @param l - integer denoting left index of the array
     * @param r - integer denoting right index of the array
     * @return integer denoting index of the array having 'x' or -1 if not found
     */
    private int performSearch(int x, int l, int r){
        int pivotIndex = findPivotIndex(l,r);
        if(data[pivotIndex] == x){
            return pivotIndex;
        }else{
            if(x > data[pivotIndex] && x <= data[r]){
                return searchUsingBinary(x, pivotIndex+1, r);
            }else{
                return searchUsingBinary(x, l, pivotIndex-1);
            }
        }
    }

    /**
     * Basic binary search implementation
     * @param x - number to be searched
     * @param l - left index of the sub-array
     * @param r - right index of the sub-array
     * @return integer denoting the index of the searched number, -1
     * if not found
     */
    private int searchUsingBinary(int x, int l, int r){
        if(l > r){
            return -1;
        }
        int mid = (l+r)/2;
        if(data[mid] == x){
            return mid;
        }else{
            if(data[mid] > x){
                return searchUsingBinary(x, l, mid-1);
            }else{
                return searchUsingBinary(x, mid+1, r);
            }
        }
    }

    private int findPivotIndex(int l, int r){
        if(l > r){
            return -1;
        }
        if(data[l] <= data[r]){
            return l;
        }
        int mid = (l+r)/2;
        /*
        If the mid element is smaller than both of it's adjacent element
        that basically means it's the pivot element.
        If not, then the pivot element will either be in right or left
        of mid element.
        So, now if the mid element is smaller than rightmost element of
        the array, then elements from mid to right most are sorted, hence
        pivot element cannot be in that part. That means pivot element must
        be on the other side of the array.
            Otherwise, it's the reverse, if mid element is larger than the
        leftmost element then pivot cannot be there in left part, and has to
        be in the right part.
         */
        if(data[mid] < data[mid+1] && data[mid] < data[mid-1]){
            return mid;
        }else if(data[mid] <= data[r]){
            return findPivotIndex(l, mid-1);
        }else{
            return findPivotIndex(mid+1, r);
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
        BasicBinarySearchWithPivot basicSearch = new BasicBinarySearchWithPivot(data);
        System.out.println(basicSearch.getPivotPosition());
        System.out.println(basicSearch.searchNumber(9));

    }
}
