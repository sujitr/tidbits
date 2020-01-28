# LRU Cache
A Least Recently Used (LRU) Cache organizes items in order of use, allowing you to quickly identify which item hasn't been used for the longest amount of time. 


### Target Costs

| Items        | Worst Case  |
| :------------- | -----:|
| space      | O(n) |
| get recently used item      |   O(1) |
| access item |    O(1) |


## Ways to implement

### With HashMap and Doubly Linked List

Strengths:

    Super fast accesses. LRU caches store items in order from most-recently used to least-recently used. That means both can be accessed in O(1) time.

    Super fast updates. Each time an item is accessed, updating the cache takes O(1) time.

Weaknesses:

    Space heavy. An LRU cache tracking 'n' items requires a double linked list of length 'n', 
    and a hash map holding 'n' items. That's O(n) space, but it's still two data structures (as opposed to one).

Visualization: 
![alt text](https://raw.githubusercontent.com/sujitr/tidbits/master/algorithms/src/main/resources/LRUCache.png "LRU With Hashmap & Doubly Linked List")


### With LinkedHashMap