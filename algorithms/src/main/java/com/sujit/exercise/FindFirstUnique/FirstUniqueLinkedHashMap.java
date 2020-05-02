package com.sujit.exercise.FindFirstUnique;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This is LinkedHashMap based approach.
 * LinkedHashMap by default maintains order of the
 * entries which is inserted, in an internal
 * doubly linked list.
 *
 * So the first element of this list will always
 * be the first unique element inserted in the
 * map.
 */
public class FirstUniqueLinkedHashMap {

    private LinkedHashMap<Integer, Integer> map;

    public FirstUniqueLinkedHashMap(int[] nums) {
        map = new LinkedHashMap<>(10);
        for(int i: nums){
            add(i);
        }
    }

    public int showFirstUnique() {
        int result = -1;
        LinkedList<Integer> listKeys = new LinkedList<>(map.keySet());
        try{
            result = listKeys.getFirst();
        }catch (NoSuchElementException nex){
            System.out.println("No keys exists in the map.");
        }
        return result;
    }

    public void add(int i) {
        if(map.containsKey(i)){
            map.remove(i); // removes the entry from map, and the internal list as well
        }else{
            map.put(i,i);
        }
    }
}
