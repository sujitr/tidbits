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
 * Time complexity : O(n)
 * Space complexity : O(1)
 */
public class OnePass {
    public static void main(String[] args){
        int[] input = {7, 1, 5, 3, 6, 4};
        int result = maxProfit(input);
        System.out.println("Max profit would be = "+result);
    }

    public static int maxProfit(int[] prices) {
        if(prices.length == 0){
            return 0;
        }
        int minPrice = prices[0];
        int maxProfit = 0;
        for(int daysPrice : prices){
            int daysProfit = daysPrice - minPrice;
            if(daysProfit > maxProfit){
                maxProfit = daysProfit;
            }
            if(daysPrice < minPrice){
                minPrice = daysPrice;
            }
        }
        return maxProfit;
    }
}
