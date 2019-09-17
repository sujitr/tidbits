package com.sujit.excercise.CustomATOI;

/**
 * Custom atoi class which behaves just like the native
 * c++ atoi function. Convert a given number string to
 * an integer.
 *
 * Consider edge cases -
 * 1. null or empty values
 * 2. +ve or -ve numbers
 * 3. white spaces
 * 4. min and max allowable handling
 */
public class CustomATOI {
    private String data;

    public CustomATOI(String input){
        this.data = input;
    }

    public int getConvertedInteger() {
        data = data.trim();
        double number = 0.0;
        // first check input
        if(data!=null && !data.isEmpty() && data.length() > 0){
            char sign = '+';
            int count = 0;
            if(data.charAt(count) == '-'){
                sign = '-';
                count++;
            }
            while(count < data.length() && data.charAt(count) >= '0' && data.charAt(count) <= '9'){
                number = number*10 + data.charAt(count) -'0';
                count++;
            }
            if(sign == '-'){
                number = -number;
            }
            if(number > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if(number < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            return (int) number;
        }else{
            throw new IllegalArgumentException("Improper input to the function");
        }
    }
}
