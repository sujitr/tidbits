package com.sujit.search;

/**
 * A classic implementation of Binary Search
 */
public class BinarySearch {
    private int[] data;

    public BinarySearch(int[] data) {
        this.data = data;
    }

    public int findIndexOf(int n){
        return search(n, 0, data.length-1);
    }

    private int search(int x, int l, int r){
        if(l > r){
            return -1;
        }

        int mid = (l+r)/2;
        if(data[mid] == x){
            return mid;
        }else{
            if(data[mid] > x){
                return search(x, l, mid-1);
            }else{
                return search(x, mid+1, r);
            }
        }
    }

    public static void main(String[] args){
        int[] data = {-12, -8, -6, -3, 4, 7, 11, 24};
        System.out.println(new BinarySearch(data).findIndexOf(11));
    }
}
