# Algorithms

## Sorting Algorithms

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