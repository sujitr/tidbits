# Find Town Judge
In a town, there are N people labelled from 1 to N.  There is a rumor that one of these people is secretly the town judge.

If the town judge exists, then:

1. The town judge trusts nobody.
2. Everybody (except for the town judge) trusts the town judge.
3. There is exactly one person that satisfies properties 1 and 2.

You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.

If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.

Example 1:

>Input: N = 2, trust = [[1,2]]   
>Output: 2

Example 2:

>Input: N = 3, trust = [[1,3],[2,3]]   
>Output: 3

Example 3:

>Input: N = 3, trust = [[1,3],[2,3],[3,1]]   
>Output: -1

Example 4:

>Input: N = 3, trust = [[1,2],[2,3]]   
>Output: -1

Example 5:

>Input: N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]   
>Output: 3


**Constraints:**

* 1 <= N <= 1000
* 0 <= trust.length <= 10^4
* trust[i].length == 2
* trust[i] are all different
* trust[i][0] != trust[i][1]
* 1 <= trust[i][0], trust[i][1] <= N

### Solution

The approach for this problem has been best described in this [video](https://www.youtube.com/watch?v=Z2Hf1_H0aao)

What it entails is, to keep count of trusts for each person, and finally 
checking for that person whose trust count = N-1. If no such person exists
then no judge exists.

So, for a given input array, denoting trusts between two person x & y as [x,y], which 
means x trusts y (not the other way around). So decrease the trust count for x and increase
trust count for y.