package com.sujit.exercise.ProfitFromShareTrade;

/**
 * Say you have an array for which the ith element is the
 * price of a given stock on day i.
 *
 * If you were only permitted to complete at most one
 * transaction (i.e., buy one and sell one share of
 * the stock), design an algorithm to find the maximum profit.
 *
 * Note that you cannot sell a stock before you buy one.
 *
 *
 * We need to find out the maximum difference
 * (which will be the maximum profit) between two numbers
 * in the given array. Also, the second number (selling price)
 * must be larger than the first one (buying price).
 *
 * In formal terms, we need to find max(prices[j]−prices[i]),
 * for every i and j such that j>i.
 *
 * Brute Force Approach:
 * Time complexity : O(n^2)
 * Space complexity : O(1)
 */
public class BruteForce {
    public static int maxProfit(int[] prices) {
        int result = 0;
        for(int i = 0; i < prices.length-1; i++){
            for(int j = i+1;j < prices.length; j++){
                if(prices[j] > prices[i]){
                    result = Math.max(prices[j]-prices[i], result);
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        int[] input = {7,6,4,3,1};
        int result = maxProfit(input);
        System.out.println("Max profit would be = "+result);
    }
}
