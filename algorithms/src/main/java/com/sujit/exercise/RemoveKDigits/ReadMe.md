# Remove K Digits
Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

Note:

* The length of num is less than 10002 and will be ≥ k.
* The given num does not contain any leading zero.

Example 1:

>Input: num = "1432219", k = 3   
>Output: "1219"   
>Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

Example 2:

>Input: num = "10200", k = 1   
>Output: "200"   
>Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

Example 3:

>Input: num = "10", k = 2   
>Output: "0"   
>Explanation: Remove all the digits from the number and it is left with nothing which is 0.

### Brute Force Approach
Time Complexity: O(kn) , where k is the count of numbers to be removed and n is the length of string.

Space Complexity: O(1) as we are not using any extra space.

This is a slightly complex problem and we will need to look at various use cases to solve this problem.

lets see some of the examples which will help us in reaching the correct algorithm.

**Example 1**: Suppose the number is 12345 and k is 1. Now we can have below cases:

    Remove 1 — Result will be 2345
    Remove 2 — Result will be 1345
    Remove 3 — Result will be 1245
    Remove 4— Result will be 1235
    Remove 5 — Result will be 1234

As you can see in this case we get the minimum number when we delete the last digit.

Now lets consider another example:

**Example 2** : Suppose the number is 12145 and k is 1. Now we can have below cases:

    Remove 1 — Result will be 2145
    Remove 2 — Result will be 1145
    Remove 1 — Result will be 1245
    Remove 4 — Result will be 1215
    Remove 5 — Result will be 1214

In this case we get the lowest digit if we remove 2 because the number next to it is smaller than 2.

***After observing the two examples we can say that if all the digits are in increasing order than we will delete the last digit , otherwise we will delete a digit for which next digit is smaller.***

Lets consider one more example to get a solid grasp of this:

Suppose the number is 10354 and k is 2 , that means we need to delete two digits.

We will start iterating the number and we will see that the number next to 1 is smaller than it. so we will remove 1.

Now the number becomes 0354. We will again start iterating it from 0.

    3 is greater than 0 so move next
    5 is greater than 3 so move next
    4 is less than 5 so delete 5

Now the number will become 034. Keep in mind that before returning we need to remove all leading zeroes.

### Smart Approach
?