# Number of Islands

This is a classic question based on graph taversals.

Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

#### Example - 1
> Input:  
>  11110  
>  11010  
>  11000  
>  00000  
>  
>  Output: 1

#### Example - 2
> Input:  
> 11000  
> 11000  
> 00100  
> 00011  
>
> Output: 3

## The approach
We would use the technique of DFS for identifying any presence
of connected components in the given graph.

BFS could also be used. 

Both has almost similar time and space complexity.

> DFS Performance 
> * time: O(m * n), you have to transverse every cell
> * space: O(m * n) for worst-case

> BFS Performance
> * time: O(m * n), you have to transverse every cell
> * space: O(m * n) for worst-case

For more details on the approaches [check here](https://medium.com/@hch.hkcontact/goldman-sachs-top-50-leetcode-questions-q26-number-of-islands-bf4a4fb3d1d4)