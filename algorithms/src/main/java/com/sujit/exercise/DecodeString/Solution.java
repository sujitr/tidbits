package com.sujit.exercise.DecodeString;

public class Solution {
    public String decodeString(String s) {
        StringBuilder sb_p = new StringBuilder(); // main StringBuilder - parent
        StringBuilder sb_c = new StringBuilder(); // sub StringBuilder - child - for intermediate ops
        int count = 1; // variable to store the number before start of the brace, repeat factor or expansion factor
        /*
        flag to track whether we are in the middle of parsing or not,
        in between braces.
         */
        boolean parseFlag = false;
        for(int i = 0; i < s.length();){
            char c = s.charAt(i);
            if(Character.isDigit(c)){ // need to find out the number before the brace start
                if(parseFlag){
                    /*
                    this means we encountered a number after opening brace, so
                    this suggests existence of embedded encoded string, which needs
                    to be expanded by recursion.
                     */
                    String sub = getSubSection(s.substring(i)); // extract the proper embedded sub-section
                    /*
                    recursive call and append the resulting expanded string to child StringBuilder
                     */
                    sb_c.append(decodeString(sub));
                    /*
                    advance the length (i) by the length of the expanded string,
                    so that next expanded stuffs can be appended after that.
                     */
                    i += sub.length(); //
                }else{
                    /*
                    There could be something like '12[ab...', so we need
                    to find the whole number as the case demands
                     */
                    if(i != 0 && Character.isDigit(s.charAt(i-1))){
                        count = count*10 + (c-48);
                    }else{
                        count = c - 48;
                    }
                    i++;
                }
            }else if(c == '['){
                parseFlag = true; // start of parsing
                i++;
            }else if(c == ']'){
                parseFlag = false; // end of parsing
                /*
                expansion is, append operation times count, of the string
                 */
                while(count > 0){
                    sb_p.append(sb_c.toString());
                    count--;
                }
                sb_c = new StringBuilder(); // re-initialize the child StringBuilder, as one cycle of embedded expansion is over
                i++;
            }else{
                /*
                for normal chars, if its an embedded op, then append to child StringBuilder
                else append to parent StringBuilder
                 */
                if(parseFlag){
                    sb_c.append(c);
                }else{
                    sb_p.append(c);
                }
                i++;
            }
        }
        // finally return the string from parent StringBuilder
        return sb_p.toString();
    }

    /**
     * Helper method to extract the correct embedded subsequence.
     * If its input is = '2[2[y]pq4[2[jk]e1[f]]]efh3[ws]', then
     * its output should be = '2[2[y]pq4[2[jk]e1[f]]]'
     *
     * @param s - String from which proper encoded sub-sequence needs to be extracted
     * @return - extracted encoded sub-sequence
     */
    private String getSubSection(String s){
        /*
        variable to track the opening & closing of braces.
        increment while opening, and decrement while closing.
         */
        int count = 0;
        for(int j = s.indexOf('['); j < s.length(); j++){
            if(s.charAt(j) == '[') {
                count++;
            }else if(s.charAt(j)==']'){
                count--;
            }
            /*
            If count reaches zero then we have reached the point when
            the main opening brace has been closed, ergo the index
            of the end point of the sub-sequence.
             */
            if(count == 0){
                return s.substring(0,j+1); // as Java substring method cuts from indexpos-1
            }
        }
        return "";
    }
}
