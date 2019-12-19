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