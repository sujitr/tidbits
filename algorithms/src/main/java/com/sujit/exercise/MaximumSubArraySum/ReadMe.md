# Maximum Subarray Sum

Given an integer array, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

> Input: [-2,1,-3,4,-1,2,1,-5,4],   
> Output: 6  
> Explanation: [4,-1,2,1] has the largest sum = 6.  

There are two approaches to solve this problem. Both are 
kind of similar to Kadane's algorithm, but varies slightly
in approach.

### Approach # 1 - Kadane's Algorithm

> Initialize:  
> * max_so_far = Minimum Integer Value  
> * max_ending_here = 0

> Loop for each element of the array  
>   * max_ending_here = max_ending_here + a[i]
>   * if(max_so_far < max_ending_here)  
>   &nbsp;&nbsp;&nbsp;&nbsp; max_so_far = max_ending_here  
>   * if(max_ending_here < 0)  
>   &nbsp;&nbsp;&nbsp;&nbsp; max_ending_here = 0  
  
> return max_so_far

Here the idea is to look for all positive contiguous segments of the array
(max_ending_here is used for this). And keep track of maximum sum 
contiguous segment among all positive segments 
 (max_so_far is used for this). Each time we get a positive sum 
 compare it with max_so_far and update max_so_far if it is greater 
 than max_so_far.
 
### Approach # 2 - Simple optimization over Kadane's Algorithm
