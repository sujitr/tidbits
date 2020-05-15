# Maximum Sub Circular Subarray
Given a circular array C of integers represented by A, find the maximum possible sum of a non-empty subarray of C.

Here, a circular array means the end of the array connects to the beginning of the array.  (Formally, C[i] = A[i] when 0 <= i < A.length, and C[i+A.length] = C[i] when i >= 0.)

Also, a subarray may only include each element of the fixed buffer A at most once.  (Formally, for a subarray C[i], C[i+1], ..., C[j], there does not exist i <= k1, k2 <= j with k1 % A.length = k2 % A.length.)

Example 1:

    Input: [1,-2,3,-2]
    Output: 3
    Explanation: Subarray [3] has maximum sum 3

Example 2:

    Input: [5,-3,5]
    Output: 10
    Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10

Example 3:

    Input: [3,-1,2,-1]
    Output: 4
    Explanation: Subarray [2,-1,3] has maximum sum 2 + (-1) + 3 = 4

Example 4:

    Input: [3,-2,2,-3]
    Output: 3
    Explanation: Subarray [3] and [3,-2,2] both have maximum sum 3

Example 5:

    Input: [-2,-3,-1]
    Output: -1
    Explanation: Subarray [-1] has maximum sum -1

Note:

    -30000 <= A[i] <= 30000
    1 <= A.length <= 30000

## Approaches
Most of the approaches to this problem are based on Kadane's Algorithm.

#### Notes and A Primer on Kadane's Algorithm
For a given array A, Kadane's algorithm can be used to find the maximum sum of the subarrays of A. Here, we only consider non-empty subarrays.

**Kadane's algorithm is based on dynamic programming**. Let dp[j] be the maximum sum of a subarray that ends in A[j]. That is,

<img src="https://latex.codecogs.com/gif.latex?\text{dp}[j]&space;=&space;\max\limits_i&space;(A[i]&space;&plus;&space;A[i&plus;1]&space;&plus;&space;\cdots&space;&plus;&space;A[j])" />

Then, a subarray ending in j+1 (such as A[i], A[i+1] + ... + A[j+1]) maximizes the A[i] + ... + A[j] part of the sum by being equal to dp[j] if it is non-empty, and 0 if it is. Thus, we have the recurrence:

<img src="https://latex.codecogs.com/gif.latex?\text{dp}[j&plus;1]&space;=&space;A[j&plus;1]&space;&plus;&space;\max(\text{dp}[j],&space;0)" title="\text{dp}[j+1] = A[j+1] + \max(\text{dp}[j], 0)" />

Since a subarray must end somewhere, <img src="https://latex.codecogs.com/gif.latex?\max\limits_j&space;dp[j]" title="\max\limits_j dp[j]" /> must be the desired answer.

To compute dp efficiently, Kadane's algorithm is usually written in the form that reduces space complexity. We maintain two variables: 'ans' as <img src="https://latex.codecogs.com/gif.latex?\max\limits_j&space;dp[j]" title="\max\limits_j dp[j]" />
and 'cur' as <img src="https://latex.codecogs.com/gif.latex?dp[j]" title="dp[j]" />; and update them as j iterates from 0 to <img src="https://latex.codecogs.com/gif.latex?A\text{.length}&space;-&space;1" title="A\text{.length} - 1" />.

Then, Kadane's algorithm is given by the following psuedocode:

    #Kadane's algorithm
    ans = cur = None
    for x in A:
        cur = x + max(cur, 0)
        ans = max(ans, cur)
    return ans 
    
### Approach #1 : Next Array
Intuition and Algorithm

Subarrays of circular arrays can be classified as either as one-interval subarrays, or two-interval subarrays, depending on how many intervals of the fixed-size buffer A are required to represent them.

For example, if A = [0, 1, 2, 3, 4, 5, 6] is the underlying buffer of our circular array, we could represent the subarray [2, 3, 4] as one interval [2, 4], but we would represent the subarray [5, 6, 0, 1] as two intervals [5, 6], [0, 1].

Using Kadane's algorithm, we know how to get the maximum of one-interval subarrays, so it only remains to consider two-interval subarrays.

Let's say the intervals are <img src="https://latex.codecogs.com/gif.latex?[0,&space;i],&space;[j,&space;A\text{.length}&space;-&space;1]" title="[0, i], [j, A\text{.length} - 1]" />. Let's try to compute the i-th candidate: the largest possible sum of a two-interval subarray for a given i.
Computing the [0,i] part of the sum is easy. Let's write

<img src="https://latex.codecogs.com/gif.latex?T_j&space;=&space;A[j]&space;&plus;&space;A[j&plus;1]&space;&plus;&space;\cdots&space;&plus;&space;A[A\text{.length}&space;-&space;1]" title="T_j = A[j] + A[j+1] + \cdots + A[A\text{.length} - 1]" />

and

<img src="https://latex.codecogs.com/gif.latex?R_j&space;=&space;\max\limits_{k&space;\geq&space;j}&space;T_k" title="R_j = \max\limits_{k \geq j} T_k" />

so that the desired i-th candidate is:

<img src="https://latex.codecogs.com/gif.latex?(A[0]&space;&plus;&space;A[1]&space;&plus;&space;\cdots&space;&plus;&space;A[i])&space;&plus;&space;R_{i&plus;2}" title="(A[0] + A[1] + \cdots + A[i]) + R_{i+2}" />

Since we can compute <img src="https://latex.codecogs.com/gif.latex?T_j" title="T_j" />​ and <img src="https://latex.codecogs.com/gif.latex?R_j" title="R_j" />​ in linear time, the answer is straightforward after this setup.

Complexity Analysis

* Time Complexity: O(N), where N is the length of A.

* Space Complexity: O(N). 

### Approach #2 : Prefix Sums + Monoqueue

