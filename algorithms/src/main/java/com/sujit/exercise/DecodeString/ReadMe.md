# Decode String
Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

Example 1:

    Input: s = "3[a]2[bc]"
    Output: "aaabcbc"

Example 2:

    Input: s = "3[a2[c]]"
    Output: "accaccacc"

Example 3:

    Input: s = "2[abc]3[cd]ef"
    Output: "abcabccdcdcdef"

Example 4:

    Input: s = "abc3[cd]xyz"
    Output: "abccdcdcdxyz"
    
## My Approach 
A recursive call while parsing the string, which takes care of 
the embedded encoded parts within the main string.

I have not used any data structures and used StringBuilder to build the 
final (and also intermediate) strings.

It works by linearly scanning the string character by character. When it
encounters a digit (or a series of digits for numbers greater that 9) then 
it starts to parse the next sequence of characters within the braces and then
append it to the parent StringBuilder, times the number previously encountered.

If it another digit inside while parsing, that means another embedded encoded sequence
is present. In this case, it first extracts the sub-sequence (till matching closing brace)
and then recursively calls the decode method again, until the embedded string is unwrapped.

  
