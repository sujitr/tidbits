# Valid Perfect Square
Given a positive integer num, write a function which returns True if num is a perfect square else False.

Note: Do not use any built-in library function such as sqrt.

Example 1:
>Input: 16   
 Output: true
 
Example 2:
> Input: 14   
Output: false

### Smart - Binary Search Approach
Using binary search, keep finding any such number between the
range of 1 to num, with which num could be fully divisible and 
whose square is also num.