# First Unique Number
You have a queue of integers, you need to retrieve the first unique integer in the queue.

Implement the FirstUnique class:

* FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
* int showFirstUnique() returns the value of the first unique integer of the queue, and returns -1 if there is no such integer.
* void add(int value) insert value to the queue.

## Approaches
Although the concept for approaching this problem is same - LRU Cache approach. 
There are 2 ways to code the solution, as in LRU Cache itself.

### HashTable & Doubly Linked List based approach
Just as in LRU Cache, we need a Hashtable, and O(1) lookup to keep the
first unique number at the 'tail' of the doubly linked list.

Add any new number if it does not exists in the map, and then add to the
linked list 'head' position.

If the number is pre-existing in the map, then its a duplicate entry, so it
need to be removed from the linked list and the map.

### Java 8 LinkedHashMap based approach
As LinkedHashMap pretty much does the same under the hood, its a nice
wrapper based way to implement the approach. 

LinkedHashMap by default keeps the entry order of the mappings intact
in an internal linked list. Although we do not have direct access
to this linked list, but casting the key-set of the map to an external Linked List 
object copies the list.

So at the end, we just need to find the first element from this list.

> Only drawback of this approach is the use of an external linked-list, which adds to storage cost = O(n+1)

 