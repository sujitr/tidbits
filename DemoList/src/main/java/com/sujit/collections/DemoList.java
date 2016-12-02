package com.sujit.collections;

import java.util.Arrays;

/**
 * A class to implement a list in Java using standard arrays. Make it generic for accepting any type of objects, to define with. 
 * This implementation has following features - 
 * <ol>
 * 	<li>List may grow from zero to infinite size</li>
 * 	<li>List will  be initialized with minimum 10 elements at the time of creation
 * 	<li>List will provide methods for fetching, adding, removing and printing the list at any state in its life-cycle
 * </ol>
 * @author Sujit
 *
 */
public class DemoList<T> {
	// to track the size of the list
	private int size = 0;
	// default size of 10 for the list
	private int DEFAULT_SIZE = 10;
	// object array to store the elements of the list
	private Object[] elements;
	
	/**
	 * Constructor
	 */
	public DemoList(){
		elements = new Object[DEFAULT_SIZE];
	}
	
	/**
	 * Method to grow the size of the list by twice the size
	 */
	private void growCapacity(){
		int newSize = elements.length*2;
		elements = Arrays.copyOf(elements, newSize);
	}
	
	/**
	 * Method to add element to the list
	 * @param t : element which needs to be added
	 */
	public void add(T t){
		if(size == elements.length){
			growCapacity();
		}
		elements[size++] = t;
	}
	
	/**
	 * Method to fetch element from list
	 * @param pos - position from where an element needs to be fetched
	 * @return fetched element
	 */
	@SuppressWarnings("unchecked")
	public T get(int pos){
		if(pos < 0 || pos > size){
			throw new ArrayIndexOutOfBoundsException("Index specified :"+pos+" while size is only from 0 to "+size);
		}
		return (T) elements[pos];
	}
	
	/**
	 * Method to remove element from list
	 * @param pos - position from where an element needs to be removed
	 * @return removed element
	 */
	@SuppressWarnings("unchecked")
	public T remove(int pos){
		if(pos < 0 || pos > size){
			throw new ArrayIndexOutOfBoundsException("Index specified :"+pos+" while size is only from 0 to "+size);
		}
		Object item = elements[pos];
		int numberOfRemainingElements = size - (pos+1);
		System.arraycopy(elements, pos+1, elements, pos, numberOfRemainingElements);
		size--;
		return (T) item;
	}
	
	/**
	 * Method for printing the list at any state in its life-cycle
	 */
	public String toString()
    {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         for(int i = 0; i < size ;i++) {
             sb.append(elements[i].toString());
             if(i<size-1){
                 sb.append(",");
             }
         }
         sb.append(']');
         return sb.toString();
    }
	
	/**
	 * Method to get the size of the list
	 * @return count of the number of the elements in the list 
	 */
    public int size() {
        return size;
    }
	
}
