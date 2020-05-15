# Algorithms

## Sorting Algorithms

### Counting Sort
<p>Counting sort algorithm is used when the specific range of the array is known.</p>
<p>The frequencies of distinct elements of the array to be sorted is counted and
stored in an auxiliary array, by mapping its value as an index of the auxiliary array.</p>

Let's assume that, array A of size N needs to be sorted.

> 1. Initialize the auxillary array ***Aux[]*** as 0. 
>   > Note: The size of this array should be ***>= max(A[])***
> 2. Traverse array ***A*** and store the count of occurrence of each 
       element in the appropriate index of the array, which means, 
       execute ***Aux[A[i]]++*** for each **_i_**, where **_i_** ranges from **_[0, N-1]_**
> 3. Initialize the empty array **_sortedA[]_**
> 4. Traverse array **_Aux_** and copy **_i_** into **_sortedA_** for **_Aux[i]_** number
      of times where **_0 <= i <= max(A[])_**

**Note:** The array A can be sorted by using this algorithm only if the 
maximum value in array A is less than the maximum size of the array Aux. 
Usually, it is possible to allocate memory up to the order of a million.
If the maximum value of A exceeds the maximum memory- allocation size, 
it is recommended that you do not use this algorithm. 
Use either the quick sort or merge sort algorithm.

### QuickSort
A quick sort first selects a value, which is called the pivot value. 
Although there are many different ways to choose the pivot value, we will simply use
the first item in the list. The role of the pivot value is to assist with splitting the list. 
The actual position where the pivot value belongs in the final sorted list, commonly called 
the split point, will be used to divide the list for subsequent calls to the quick sort.

> QuickSort has two steps: a partition step and a recursive step.

Partitioning begins by locating two position markers at the beginning and end of the remaining 
items in the list. The goal of the partition process is to move items that are on the wrong side 
with respect to the pivot value while also converging on the split point.

The quick sort uses divide and conquer to gain the same advantages as the merge sort, 
while not using additional storage. As a trade-off, however, it is possible that the list may 
not be divided in half. When this happens, we will see that performance is diminished.

[Check here for more description](https://runestone.academy/runestone/books/published/pythonds/SortSearch/TheQuickSort.html)

> Good to know : Java Arrays.sort() method uses dual pivot QuickSort. 
> To know more about that check out [this](https://cs.stanford.edu/~rishig/courses/ref/l11a.pdf)

### Selection Sort
The most simplest of sorting algorithms. Child's sort.
You start with first element and compare with every other element in array.
If you find any other element smaller than it then swap'em. Then you move 
onto the next element and repeat the process over again, till you reach last
element of the array. 

### Bubble Sort
Second most simplest of sorting algorithms. Keep comparing two neighboring
elements, swapping them if they are out of order. Keep doing that till you 
go about all the elements in the array. This would ensure the highest element(s)
bubble up to the rightmost position(s).

> A very good blog on reading basic CS concepts. Explains in easy and fun way.
> Can be used for a quick refresher - [check out](https://medium.com/basecs)

# Graph

What is a Graph? Simply put, its a non-linear unrestricted data structure. Think 
it as a superset of Tree, where all the restrictions of Tree goes out of the window.
No 'root' node, no unidirectional flow. All the mess you can think of without those
rules are a Graph.

![Tree data structures as compared to graph data structures](https://miro.medium.com/max/1400/1*rguQ2Y2Z920IYGkO0cHHtQ.jpeg)

For details - [Check this Gentle Introduction to Graph Theory](https://medium.com/basecs/a-gentle-introduction-to-graph-theory-77969829ead8)

## Graph Representations
There are two ways to represent graph using data structures - 
* Adjacency Matrix
* Adjacency List (Arrays of Lists or Linked-Lists)

## Connected Components in Graph
A connected component of an undirected graph is a subgraph in which every two vertices are connected to each other by a path(s), and which is connected to no other vertices outside the subgraph.

Below graph has three connected components - 
![Connected components in a Graph](https://media.geeksforgeeks.org/wp-content/uploads/connectedcomponents.png)

A graph where all vertices are connected with each other has exactly one connected component, 
consisting of the whole graph. Such a graph with only one connected component is called a Strongly Connected Graph.

## Graph Traversal 
Graph traversal (also known as graph search) refers to the process of 
visiting (checking and/or updating) each vertex in a graph. 
Such traversals are classified by the order in which the vertices are visited. 
We need to traverse a graph to perform any meaningful action, like search within 
the graph.\n

There are two classic ways to traverse graphs - 

1. **DFT (DFS)** - [Depth First approach](https://medium.com/basecs/deep-dive-through-a-graph-dfs-traversal-8177df5d0f13)
2. **BFT (BFS)** - Breadth First approach

![Comparison of approaches](https://miro.medium.com/max/1440/1*_v6x7az3pWGaBWYo-fYMwg.jpeg)

## Graph Search
Extracting meaningful insights from the traversals of a graph.

![Whats matter in search objectives?](https://miro.medium.com/max/1728/1*ri9EgM7xLmrZmywgwt96pQ.jpeg)

1. Whether a path even exists between two nodes? – can be done using DFS

2. What is the shortest path between two nodes? - Technically, Breadth-first search (BFS) by itself does not let you find the shortest path, simply because BFS is not looking for a shortest path: BFS describes a strategy for searching a graph, but it does not say that you must search for anything in particular. Dijkstra's algorithm adapts BFS to let you find single-source shortest paths.

3. In order to retrieve the shortest path from the origin to a node, you need to maintain two items for each node in the graph: its current shortest distance, and the preceding node in the shortest path. Initially all distances are set to infinity, and all predecessors are set to empty. In your example, you set A's distance to zero, and then proceed with the BFS. On each step you check if you can improve the distance of a descendant, i.e. the distance from the origin to the predecessor plus the length of the edge that you are exploring is less than the current best distance for the node in question. If you can improve the distance, set the new shortest path, and remember the predecessor through which that path has been acquired. When the BFS queue is empty, pick a node (in your example, it's E) and traverse its predecessors back to the origin. This would give you the shortest path.

4. For more advanced graph search algorithms, check - 
    1. https://cs.stanford.edu/people/abisee/gs.pdf
    2. https://www.redblobgames.com/pathfinding/a-star/introduction.html
   
   
# Trie
Trie (we pronounce "try") or prefix tree is a tree data structure, which is used for retrieval of a key in a dataset of strings. There are various applications of this very efficient data structure such as :

1. [Autocomplete](https://en.wikipedia.org/wiki/Autocomplete)
2. [Spell Checker](https://en.wikipedia.org/wiki/Spell_checker)
3. [IP routing (Longest prefix matching)](https://en.wikipedia.org/wiki/Longest_prefix_match)
4. [T9 predictive text](https://en.wikipedia.org/wiki/T9_(predictive_text\))
5. [Solving word games](https://en.wikipedia.org/wiki/Boggle)

There are several other data structures, like balanced trees and hash tables, which give us the possibility to search for a word in a dataset of strings. Then why do we need trie? Although hash table has O(1) time complexity for looking for a key, it is not efficient in the following operations :

* Finding all keys with a common prefix.
* Enumerating a dataset of strings in lexicographical order.

Another reason why trie outperforms hash table, is that as hash table increases in size, there are lots of hash collisions and the search time complexity could deteriorate to O(n), where n is the number of keys inserted. Trie could use less space compared to Hash Table when storing many keys with the same prefix. In this case using trie has only O(m) time complexity, where m is the key length. Searching for a key in a balanced tree costs O(m log n) time complexity.

### Trie Node Structure
Trie is a rooted tree. Its nodes have the following fields:

* Maximum of R links to its children, where each link corresponds to one of R character values from dataset alphabet. In this article we assume that R is 26, the number of lowercase latin letters.
* Boolean field which specifies whether the node corresponds to the end of the key, or is just a key prefix.

![Representation of a key "leet" in trie](https://raw.githubusercontent.com/sujitr/tidbits/master/algorithms/src/main/resources/Trie_Node.png)

Two of the most common operations in a trie are insertion of a key and search for a key.

### Insertion of a key to a trie

We insert a key by searching into the trie. We start from the root and search a link, which corresponds to the first key character. There are two cases :

* A link exists. Then we move down the tree following the link to the next child level. The algorithm continues with searching for the next key character.
* A link does not exist. Then we create a new node and link it with the parent's link matching the current key character. We repeat this step until we encounter the last character of the key, then we mark the current node as an end node and the algorithm finishes.

![Insertion of keys into a trie](https://raw.githubusercontent.com/sujitr/tidbits/master/algorithms/src/main/resources/TrieInsert.png)

#### Complexity Analysis
     
* Time complexity : O(m), where m is the key length.
     
In each iteration of the algorithm, we either examine or create a node in the trie till we reach the end of the key. This takes only 'm' operations.
     
* Space complexity : O(m).
     
In the worst case newly inserted key doesn't share a prefix with the the keys already inserted in the trie. We have to add 'm' new nodes, which takes us O(m) space.

### Search for a key in a trie

Each key is represented in the trie as a path from the root to the internal node or leaf. We start from the root with the first key character. We examine the current node for a link corresponding to the key character. There are two cases :

* A link exist. We move to the next node in the path following this link, and proceed searching for the next key character.

* A link does not exist. If there are no available key characters and current node is marked as 'isEnd' we return true. Otherwise there are possible two cases in each of them we return false :
    * There are key characters left, but it is impossible to follow the key path in the trie, and the key is missing.
    * No key characters left, but current node is not marked as 'isEnd'. Therefore the search key is only a prefix of another key in the trie.
    
#### Complexity Analysis

* Time complexity : O(m) In each step of the algorithm we search for the next key character. In the worst case the algorithm performs 'm' operations.

* Space complexity : O(1)

### Search for a key prefix in a trie
The approach is very similar to the one we used for searching a key in a trie. We traverse the trie from the root, till there are no characters left in key prefix or it is impossible to continue the path in the trie with the current key character. The only difference with the mentioned above search for a key algorithm is that when we come to an end of the key prefix, we always return true. We don't need to consider the isEnd mark of the current trie node, because we are searching for a prefix of a key, not for a whole key.

#### Complexity Analysis

* Time complexity : O(m)
* Space complexity : O(1)








