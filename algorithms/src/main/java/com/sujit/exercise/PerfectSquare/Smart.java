package com.sujit.exercise.PerfectSquare;

public class Smart {
    private int num;

    public Smart(int num) {
        this.num = num;
    }

    public boolean isPerfectSquare() {
        if(num == 0 || num == 1){
            return true;
        }
        int left = 0;
        int right = num;
        while(left <= right){
            try{
                int mid = (right+left)/2;
                if(num%mid==0 && mid*mid==num){
                    return true;
                }else if(mid < (num/mid)){
                    left = mid+1;
                }else{
                    right = mid-1;
                }
            }catch(ArithmeticException aex){
                /*
                IMPORTANT: This is for the cases where the
                range is really small, for a input
                like '5', where the left can be easily
                0 after one loop.
                */
                return false;
            }

        }
        return false;
    }
}
