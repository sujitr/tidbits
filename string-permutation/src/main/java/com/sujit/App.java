package com.sujit;

import java.util.Set;
import java.util.HashSet;

public class App {
    public static void main( String[] args ){
        String test = "ABCD";
        System.out.println(">> - "+permutate(test));
    }

    /**
     * Recursive function which just takes out the first character from the string
     * then calls itself recursively and then from the obtained words, add that character
     * between all positions of that word, for all the words.
     * It stops when it detects it has run out of the characters in word.
     */
    public static Set<String> permutate(String arg){
        Set<String> results = new HashSet<String>();
        if(arg == null)
            return null;
        else if(arg.length() == 0){
            results.add("");
            return results;
        }
        char firstChar = arg.charAt(0);
        String remaining = arg.substring(1);
        Set<String> remWords = permutate(remaining);
        for(String word : remWords){
            for(int i =0; i <= word.length(); i++)
                results.add(resultPositionalAdder(word,firstChar,i));
        }  
        return results;
    }
    
    /**
     * Method to insert the first character in between the word at the specified position
     * and returning the complete string
     */
    public static String resultPositionalAdder(String word, char firstChar, int position){
        StringBuilder sb = new StringBuilder();
        sb.append(word.substring(0,position)).append(firstChar).append(word.substring(position));
        return sb.toString();
    }
    
}
