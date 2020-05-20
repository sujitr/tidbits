# Permutation in String
Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, one of the first string's permutations is the substring of the second string.

This problem is similar to another problem of "**Find All Anagrams in a String**"

Example 1:

> Input: s1 = "ab" s2 = "eidbaooo"   
> Output: True   
>Explanation: s2 contains one permutation of s1 ("ba").

Example 2:

>Input:s1= "ab" s2 = "eidboaoo"   
Output: False



Note:

    The input strings only contain lower case letters.
    The length of both given strings is in range [1, 10,000].

## Approaches

There are many approaches to this problem, from brute force, to sorting to 
other advanced techniques. Mind you, the more the basic form of approach (
be it brute force etc, more the chances of TLE - Time Limit Exceeded).

So it's always better to start with a simple approach but refine it to
achieve optimum solutions.

### Brute Force (TLE)
The simplest method is to generate all the permutations of the short string and to check if the generated permutation is a substring of the longer string.

In order to generate all the possible pairings, we make use of a function permute(string_1, string_2, current_index). This function creates all the possible permutations of the short string s1s1s1.

To do so, permute takes the index of the current element currentindexcurrent_indexcurrenti​ndex as one of the arguments. Then, it swaps the current element with every other element in the array, lying towards its right, so as to generate a new ordering of the array elements. After the swapping has been done, it makes another call to permute but this time with the index of the next element in the array. While returning back, we reverse the swapping done in the current function call.

Thus, when we reach the end of the array, a new ordering of the array's elements is generated.

##### Complexity Analysis

* Time complexity : O(n!). We match all the permutations of the short string s1, of length s1, with s2. Here, n refers to the length of s1.

* Space complexity : O(n^2). The depth of the recursion tree is n(n refers to the length of the short string s1). Every node of the recursion tree contains a string of max length n.

### Using Sorting (TLE)
The idea behind this approach is that one string will be a permutation of another string only if both of them contain the same characters the same number of times. One string x is a permutation of other string y only if sorted(x)=sorted(y).

In order to check this, we can sort the two strings and compare them. We sort the short string s1 and all the substrings of s2, sort them and compare them with the sorted s1 string. If the two match completely, s1's permutation is a substring of s2, otherwise not.

##### Complexity Analysis

* Time Complexity : <img src="https://latex.codecogs.com/gif.latex?O\big(l_1log(l_1)&plus;(l_2-l_1)l_1log(l_1)\big)" title="O\big(l_1log(l_1)+(l_2-l_1)l_1log(l_1)\big)" />, where 
<img src="https://latex.codecogs.com/gif.latex?l_1" title="l_1" /> is the length of the string <img src="https://latex.codecogs.com/gif.latex?l_1" title="l_1" />
and <img src="https://latex.codecogs.com/gif.latex?l_2" title="l_2" /> is length of the string <img src="https://latex.codecogs.com/gif.latex?l_2" title="l_2" />

* Space Complexity : <img src="https://latex.codecogs.com/gif.latex?O(l_1)" title="O(l_1)" />, 't' array is used.

### Using HashMap (TLE)
One string will be a permutation of another string only if both of them contain the same characters with the same frequency. We can consider every possible substring in the long string s2 of the same length as that of s1 and check the frequency of occurence of the characters appearing in the two. If the frequencies of every letter match exactly, then only s1's permutation can be a substring of s2.

In order to implement this approach, instead of sorting and then comparing the elements for equality, we make use of a hashmap s1map which stores the frequency of occurence of all the characters in the short string s1. We consider every possible substring of s2 of the same length as that of s1, find its corresponding hashmap as well, namely s2map. Thus, the substrings considered can be viewed as a window of length as that of s1 iterating over s2. If the two hashmaps obtained are identical for any such window, we can conclude that s1's permutation is a substring of s2, otherwise not.

##### Complexity Analysis
      
* Time Complexity : <img src="https://latex.codecogs.com/gif.latex?O(l_1&plus;26*l_1*(l_2-l_1))" title="O(l_1+26*l_1*(l_2-l_1))" />, hashmap contains atmost 26 keys. And where 
    <img src="https://latex.codecogs.com/gif.latex?l_1" title="l_1" /> is the length of the string <img src="https://latex.codecogs.com/gif.latex?l_1" title="l_1" />
    and <img src="https://latex.codecogs.com/gif.latex?l_2" title="l_2" /> is length of the string <img src="https://latex.codecogs.com/gif.latex?l_2" title="l_2" />
           
* Space Complexity : O(1), hashmap contains atmost 26 key-value pairs.

### Using Arrays
Instead of making use of a special HashMap datastructure just to store the frequency of occurence of characters, we can use a simpler array data structure to store the frequencies. Given strings contains only lowercase alphabets ('a' to 'z'). So we need to take an array of size 26.The rest of the process remains the same as the last approach.

##### Complexity Analysis
Same as using HashMap, just Hashmaps being replaced with arrays to avoid TLE.

### Using Sliding Window
Instead of generating the hashmap afresh for every window considered in s2, we can create the hashmap just once for the first window in s2. Then, later on when we slide the window, we know that we add one preceding character and add a new succeeding character to the new window considered. Thus, we can update the hashmap by just updating the indices associated with those two characters only. Again, for every updated hashmap, we compare all the elements of the hashmap for equality to get the required result.

##### Complexity Analysis
Same as using HashMap above.

### Using Optimized Sliding Window
The last approach can be optimized, if instead of comparing all the elements of the hashmaps for every updated s2map corresponding to every window of s2 considered, we keep a track of the number of elements which were already matching in the earlier hashmap and update just the count of matching elements when we shift the window towards the right.

To do so, we maintain a count variable, which stores the number of characters(out of the 26 alphabets), which have the same frequency of occurence in s1 and the current window in s2. When we slide the window, if the deduction of the last element and the addition of the new element leads to a new frequency match of any of the characters, we increment the count by 1. If not, we keep the count intact. But, if a character whose frequency was the same earlier(prior to addition and removal) is added, it now leads to a frequency mismatch which is taken into account by decrementing the same count variable. If, after the shifting of the window, the count evaluates to 26, it means all the characters match in frequency totally. So, we return a True in that case immediately.


                                                                                   