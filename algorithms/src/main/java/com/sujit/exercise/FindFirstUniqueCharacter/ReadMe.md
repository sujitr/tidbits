# First Unique Character in a String
Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1. 

Note: You may assume the string contain only lowercase letters. 

### Examples:
    
    s = "leetcode"
    return 0.
    
    s = "loveleetcode",
    return 2.


## Approaches 

### 1. Java 8 Brute Force
For every character in the string, check if that character 
is repeated from the next index till the end of the string.

Use the Java String API's 'indexOf' method to its advantage.

### 2. Java 8 Smart
> This has the best runtime among all approaches

For every character from 'a' to 'z', check if its current
index in the string is also its 'lastIndex' (Java String API method)
in the string, and if so, the 'min' count of all such instances
will give away the first unique character.

### 3. Character value array
This is based on the hint from the problem that only lowercase
characters are allowed. 

We can keep an array of 26 characters which will keep the count
of each character in the string. Then we loop through the string
and check in the count array if the current characters count is 1.
