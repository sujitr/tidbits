# Array Rotation
Given an array, rotate the array to the right by k steps, where k is non-negative.

> Input: [1,2,3,4,5,6,7] and k = 3

> Output: [5,6,7,1,2,3,4]

***
There are **Three** approaches for this problem - 

1. **Brute Force**
    * Rotate the array one element at a time
    * Do that for 'k' number of times
    * O(n<sup>k</sup>) - time complexity
    * O(1) - space complexity
2. **Split-Reverse-Merge-Reverse**
    * Split the array at the 'k'th position
    * Reverse both the sub arrays
    * Join the sub-arrays after reversing
    * Reverse the joined array once more
3. **Mathematical Approach**
    * The **k'th** rotated position of any element which is currently at position **i** is = **(i+k)%len**, where len is the length of the array
    * O(n) - time complexity
    * O(1) - space complexity

