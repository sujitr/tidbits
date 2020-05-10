# Ransom Note
Given an arbitrary ransom note string and another string containing letters from all the magazines, write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.

Each letter in the magazine string can only be used once in your ransom note.

Example 1:

> Input: ransomNote = "a", magazine = "b"   
> Output: false

Example 2:

> Input: ransomNote = "aa", magazine = "ab"   
> Output: false

Example 3:

> Input: ransomNote = "aa", magazine = "aab"   
> Output: true


Constraints:

    You may assume that both strings contain only lowercase letters.


### Brute Force Approach
Use a Hash-map and count & remove letters based on occurrence. 
Simple but not smart.

### Smart Approach
Since its a given that both the string contain only lowercase letters, 
this information could be used to solve the program with a simple
character array and in-place counting based on number of occurrences
of a letter.