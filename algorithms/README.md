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


# Graph

## Graph Representations
There are two ways to represent graph using data structures - 
* Adjacency Matrix
* Adjacency List (Arrays of Lists or Linked-Lists)

## Graph Traversal 
Graph traversal (also known as graph search) refers to the process of 
visiting (checking and/or updating) each vertex in a graph. 
Such traversals are classified by the order in which the vertices are visited. 
We need to traverse a graph to perform any meaningful action, like search within 
the graph.\n

There are two classic ways to traverse graphs - 

1. **DFT (DFS)** - Depth First approach
2. **BFT (BFS)** - Breadth First approach

![Comparison of approaches](https://miro.medium.com/max/1440/1*_v6x7az3pWGaBWYo-fYMwg.jpeg)

##Graph Search
Extracting meaningful insights from the traversals of a graph.

![Whats matter in search objectives?](https://miro.medium.com/max/1728/1*ri9EgM7xLmrZmywgwt96pQ.jpeg)

1. Whether a path even exists between two nodes? – can be done using DFS

2. What is the shortest path between two nodes? - Technically, Breadth-first search (BFS) by itself does not let you find the shortest path, simply because BFS is not looking for a shortest path: BFS describes a strategy for searching a graph, but it does not say that you must search for anything in particular. Dijkstra's algorithm adapts BFS to let you find single-source shortest paths.

3. In order to retrieve the shortest path from the origin to a node, you need to maintain two items for each node in the graph: its current shortest distance, and the preceding node in the shortest path. Initially all distances are set to infinity, and all predecessors are set to empty. In your example, you set A's distance to zero, and then proceed with the BFS. On each step you check if you can improve the distance of a descendant, i.e. the distance from the origin to the predecessor plus the length of the edge that you are exploring is less than the current best distance for the node in question. If you can improve the distance, set the new shortest path, and remember the predecessor through which that path has been acquired. When the BFS queue is empty, pick a node (in your example, it's E) and traverse its predecessors back to the origin. This would give you the shortest path.

4. For more advanced graph search algorithms, check - 
    1. https://cs.stanford.edu/people/abisee/gs.pdf
    2. https://www.redblobgames.com/pathfinding/a-star/introduction.html
    
[Graph Material Sources](https://medium.com/basecs/deep-dive-through-a-graph-dfs-traversal-8177df5d0f13)