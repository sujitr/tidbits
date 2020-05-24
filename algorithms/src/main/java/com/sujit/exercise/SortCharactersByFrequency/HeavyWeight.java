package com.sujit.exercise.SortCharactersByFrequency;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Using Collection classes of Java
 */
public class HeavyWeight {
    public String frequencySort(String s) {
        if(s.length()<1) return "";
        Map<Character, Integer> countMap = new HashMap();
        for(char c : s.toCharArray()){
            countMap.put(c, countMap.getOrDefault(c,0)+1);
        }
        Map<Character, Integer> reverseSortedCountMap = countMap
                .entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y)->x, LinkedHashMap::new));

        StringBuilder sb = new StringBuilder();
        reverseSortedCountMap.entrySet().stream().forEach(entry->{
            int count = entry.getValue();
            while(count>0){
                sb.append(entry.getKey()); count--;
            }
        });
        return sb.toString();
    }
}
