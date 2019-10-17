# Find Smallest Missing Integer in Array

Given an array of integers, find the first missing positive integer in linear time and constant space. In other words, find the lowest positive integer that does not exist in the array. The array can contain duplicates and negative numbers as well.

For example, the input **[3, 4, -1, 1]** should give **2**. The input **[1, 2, 0]** should give **3**.

You can modify the input array in-place.

## Approaches to solve the problem

* **Naive approach** - O(n*2) - Search all positive integers, starting from 1 in the array.
* **Sort approach** - O(nLogn) - Once the array is sorted, then all we need to do is a linear scan of the array 
* **HashMap approach** - O(n) but with O(n) space - We can build a hash table of all positive elements in the array. Then we
can look in the hash table for all positive integers, starting from 1. As soon as we find a number which is not there in
hash table, we return it.
* **Linear time approach** - O(n) - We use array elements as index. To mark presence of an element x, we change the value
at the index x to negative. This approach will not work with negative numbers, so we segregate +ve from -ve numbers and 
then apply this approach. 

Following is the two step algorithm for Linear Time Approach - 
1. Segregate positive numbers from others i.e., move all non-positive numbers to left side. In the following code, segregate() function does this part.

2. Now we can ignore non-positive elements and consider only the part of array which contains all positive elements. 
We traverse the array containing all positive numbers and to mark presence of an element x, we change the sign of value at index x to negative. We traverse the array again and print the first index which has positive value.