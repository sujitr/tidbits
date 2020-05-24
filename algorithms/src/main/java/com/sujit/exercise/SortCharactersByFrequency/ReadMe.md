# Sort Characters By Frequency
Given a string, sort it in decreasing order based on the frequency of characters.

Example 1:

>Input:
"tree"

>Output:
"eert" OR "eetr"

Example 2:

>Input:
"cccaaa"

>Output:
"cccaaa" OR "aaaccc"

Example 3:

>Input:
"Aabb"

>Output:
"bbAa" OR "bbaA"

##Approaches

### Using Java Collection classes - heavyweight
Use a hash map to count the frequency of each character and then 
reverse sort the map to get the result string. Kind of brute force.  


### Using arrays - lightweight
Count the frequency of the characters in an array of length 128, to 
account for full ASCII characters. As we are dealing with both 
lower and upper case characters. Once counted, just form the result 
string by counting max to min.

