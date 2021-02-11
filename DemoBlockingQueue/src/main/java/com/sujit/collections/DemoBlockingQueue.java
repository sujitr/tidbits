package com.sujit.collections;

import java.util.Queue;
import java.util.LinkedList;

/**
 * A custom implementation class of the blocking queue.
 * This class attempts to replicate the functionality of
 * java standard class 'BlockingQueue'.
 * @author Sujit Roy
 *
 */
public class DemoBlockingQueue {

	  @SuppressWarnings("rawtypes")
	private Queue queue = new LinkedList();
	  private int  limit = 5;

	  /**
	   * Constructor with the maximum limit for the queue
	   * @param limit
	   */
	  public MyBlockingQueue(int limit){
	    this.limit = limit;
	  }


	  /**
	   * Method to enqueue an object in the blocked queue. This method
	   * blocks the enqueue thread if the queue is already full, until some
	   * other thread makes space in the queue.
	   * @param item
	   * @throws InterruptedException
	   */
	  @SuppressWarnings("unchecked")
	public synchronized void put(Object item) throws InterruptedException  {
	    while(isQueueCapped()) {
	      wait();
	    }
	    if(isQueueEmpty()) {
	      notifyAll();
	    }
	    this.queue.add(item);
	  }


	  /**
	   * Method to dequeue an object from the blocked queue. This method
	   * blocks the dequeue thread if the queue is empty, until some other thread
	   * inserts an item into the queue.
	   * @return
	   * @throws InterruptedException
	   */
	  public synchronized Object get() throws InterruptedException{
	    while(isQueueEmpty()){
	      wait();
	    }
	    if(isQueueCapped()){
	      notifyAll();
	    }
	    return this.queue.poll();
	  }

	  /**
	   * Method to check if the queue is full
	   * @return
	   */
	  private boolean isQueueCapped() {
	        return this.queue.size() == limit;
	  }

	  /**
	   * Method to check if the queue is empty
	   * @return
	   */
	  private boolean isQueueEmpty() {
	        return this.queue.size() == 0;
	  }

	}
