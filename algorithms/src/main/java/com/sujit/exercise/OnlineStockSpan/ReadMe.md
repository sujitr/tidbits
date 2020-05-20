# Online Stock Span
Write a class StockSpanner which collects daily price quotes for some stock, and returns the span of that stock's price for the current day.

The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backwards) for which the price of the stock was less than or equal to today's price.

For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85], then the stock spans would be [1, 1, 1, 2, 1, 4, 6].

**Example 1:**

    Input: ["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
    Output: [null,1,1,1,2,1,4,6]
    Explanation: 
    First, S = StockSpanner() is initialized.  Then:
    S.next(100) is called and returns 1,
    S.next(80) is called and returns 1,
    S.next(60) is called and returns 1,
    S.next(70) is called and returns 2,
    S.next(60) is called and returns 1,
    S.next(75) is called and returns 4,
    S.next(85) is called and returns 6.
    
    Note that (for example) S.next(75) returned 4, because the last 4 prices
    (including today's price of 75) were less than or equal to today's price.

**Note:**

* Calls to StockSpanner.next(int price) will have 1 <= price <= 10^5.
* There will be at most 10000 calls to StockSpanner.next per test case.
* There will be at most 150000 calls to StockSpanner.next across all test cases.
* The total time limit for this problem has been reduced by 75% for C++, and 50% for all other languages.



## Approaches

### 1. Using Stack
Clearly, we need to focus on how to make each query faster than a linear scan. In a typical case, we get a new element like 7, and there are some previous elements like 11, 3, 9, 5, 6, 4. Let's try to create some relationship between this query and the next query.

If (after getting 7) we get an element like 2, then the answer is 1. So in general, whenever we get a smaller element, the answer is 1.

If we get an element like 8, the answer is 1 plus the previous answer (for 7), as the 8 "stops" on the same value that 7 does (namely, 9).

If we get an element like 10, the answer is 1 plus the previous answer, plus the answer for 9.

Notice throughout this evaluation, we only care about elements that occur in increasing order - we "shortcut" to them. That is, from adding an element like 10, we cut to 7 [with "weight" 4], then to 9 [with weight 2], then cut to 11 [with weight 1].

A stack is the ideal data structure to maintain what we care about efficiently.

#### Algorithm

Let's maintain a weighted stack of decreasing elements. The size of the weight will be the total number of elements skipped. For example, 11, 3, 9, 5, 6, 4, 7 will be (11, weight=1), (9, weight=2), (7, weight=4).

When we get a new element like 10, this helps us count the previous values faster by popping weighted elements off the stack. The new stack at the end will look like (11, weight=1), (10, weight=7).

#### Complexity Analysis

* Time Complexity: O(Q), where Q is the number of calls to StockSpanner.next. In total, there are Q pushes to the stack, and at most Q pops.

* Space Complexity: O(Q). 