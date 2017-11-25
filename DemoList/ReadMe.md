List demonstration in Java with Arrays
======================================

This is a usual interview question, to implement a list data structure in Java with arrays as fundamental block.

## General Features of the List should be
1. Make it __generic__ for accepting any type of objects, to define with
  * This is an interesting topic. __Creating a generic array is not straightforward in Java__. This particular example uses Object array in the background. More discussions on this could be found [here](https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java)
2. List may grow from zero to infinite size
3. List will  be initialized with minimum 10 elements at the time of creation
4. List will provide methods for fetching, adding, removing and printing the list at any state in its life-cycle