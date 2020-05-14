package com.sujit.exercise.RemoveKDigits;

public class BruteForce {
    private String num;

    public BruteForce(String num) {
        this.num = num;
    }

    /**
     * Method to remove K digits
     * @param k number of digits to be removed
     * @return Updated string representing new number after removing K digits
     */
    public String removeKdigits(int k) {
        // if number of digits to be removed equal to number of input digits
        if(num.length()==k){
            return "0";
        }
        char[] nums = num.toCharArray();
        /*
        loop till you reach a remnant array of length = 2
        beyond which the only '.' (if any) will be removed and
        the remaining single digit will be answer, subject
        to leading zero and only zero check. Or if no '.' is there
        then, remnant digits are the answer.
         */
        while(k > 0 && nums.length>=2){
            boolean removed = false;
            for(int i = 0; i < nums.length-1; i++){
                if(nums[i]-'0' > nums[i+1]-'0'){
                    nums[i] = '.';
                    removed=true;
                    break;
                }
            }
            if(!removed){
                nums[nums.length-1]='.';
            }
            k--;
            char[] newNums = new char[nums.length-1];
            for(int i = 0, j = 0; i < nums.length; i++){
                if(nums[i]!='.'){
                    newNums[j]=nums[i];
                    j++;
                }
            }
            nums = newNums;
        }
        // removing all leading zeroes
        int firstNonZeroIndex = -1;
        for(int i = 0; i < nums.length; i++){
            if(nums[i]!='0'){
                firstNonZeroIndex = i;
                break;
            }
        }
        if(firstNonZeroIndex>0){
            char[] trimNums = new char[nums.length-firstNonZeroIndex];
            for(int i = firstNonZeroIndex, j=0; i < nums.length; i++,j++){
                trimNums[j] = nums[i];
            }
            nums = trimNums;
        }
        // handling only zeroes
        boolean allZero = true;
        for(int i = 0; i < nums.length; i++){
            if(nums[i]!='0'){
                allZero = false;
                break;
            }
        }
        return (allZero)?"0":new String(nums);
    }
}
