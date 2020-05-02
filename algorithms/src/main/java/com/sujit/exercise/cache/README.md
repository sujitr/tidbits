# LRU Cache
A Least Recently Used (LRU) Cache organizes items in order of use, 
allowing you to quickly identify which item hasn't been used for 
the longest amount of time.

When a cache miss occurs, such an item is evicted first, and then
the new item is placed in the cache.

Hence, from this eviction policy of removal of least used item, the name
of the cache mechanism.


### Target Costs

| Items        | Worst Case  |
| :------------- | -----:|
| space      | O(n) |
| get recently used item      |   O(1) |
| access item |    O(1) |


## Ways to implement

### With HashMap and Doubly Linked List

An LRU Cache should support fast lookup. Apparently, in order to
achieve fast lookup, we need to use Hashtable or HashMap.

Also, an LRU Cache requires that insert and delete operations
should be in O(1) time. The obvious choice for this requirement
is Linked List. Linked List support insert/delete operations
in O(1) time if we have the reference of element.

While building an LRU cache requires that you think in terms of
these two data structures. The reality is that these two data
structures actually work coherently to achieve the design.

Strengths:

    Super fast accesses. LRU caches store items in order from most-recently used to least-recently used. That means both can be accessed in O(1) time.

    Super fast updates. Each time an item is accessed, updating the cache takes O(1) time.

Weaknesses:

    Space heavy. An LRU cache tracking 'n' items requires a double linked list of length 'n', 
    and a hash map holding 'n' items. That's O(n) space, but it's still two data structures (as opposed to one).

Visualization: 
![alt text](https://raw.githubusercontent.com/sujitr/tidbits/master/algorithms/src/main/resources/lru-cache-2.png "LRU With Hashmap & Doubly Linked List")

More resources:

1. [Site](https://www.interviewcake.com/concept/java/lru-cache)

### With LinkedHashMap

The [LinkedHashMap](https://www.baeldung.com/java-linked-hashmap) class is very similar to HashMap in most aspects. 
However, the linked hash map is based on both hash table and linked list to enhance the functionality of hash map.

It maintains a doubly-linked list running through all its entries in addition to an underlying array of default size 16.

It is an ordered Map. You can specify how you want to order the map, by insertion or by access.
It keeps track of the order in which each entry is added.
By default, it removes the oldest entry when reached a threshold.

Hence we can use this JDK provided structure directly instead of creating our own version to use as an LRU Cache.
That involves following steps.


1. A special [constructor](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#LinkedHashMap-int-float-boolean-) 
is provided to create a linked hash map whose order of iteration is the order in which its entries were last accessed, from least-recently accessed to most-recently (access-order). This kind of map is well-suited to building LRU caches.

2. The [removeEldestEntry(Map.Entry)](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#removeEldestEntry-java.util.Map.Entry-) method may be overridden to impose a policy for removing stale mappings automatically when new mappings are added to the map. 

References:
For a good visual representation of LinkedHashMap working, please refer [here](https://medium.com/@mr.anmolsehgal/java-linkedhashmap-internal-implementation-44e2e2893036)