package com.sujit.exercise.NumberOfIslands;

public class CountIslands {
    private char[][] grid;

    public CountIslands(char[][] graph){
        this.grid = graph;
    }

    /**
     * Method to return the number of islands
     * @return
     */
    public int getCount() {
        if(null == grid || grid.length==0) return 0;
        int numIslands = 0;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                // check the cell for island count if only its value is 1
                if(grid[i][j]=='1'){
                    numIslands += dfs(grid, i, j);
                }
            }
        }

        return numIslands;

    }

    /**
     * Method to perform DFS based check on a cell and its
     * neighboring cells.
     * @param map
     * @param i
     * @param j
     * @return
     */
    private int dfs(char[][] map, int i, int j){
        /*
        while checking a particular cell during DFS, here we
        do not care if the value in the cell is '0', or its
        position is outside the given array boundary, as in
        those cases the cell is just water.

        This is the exit condition/base case for the recursive call.
        */
        if(i < 0 || j < 0 || i >= map.length || j >= map[i].length || map[i][j]=='0') return 0;

        /* now mark the current cell as 'visited' by changing its value to '0'.
         */
        map[i][j] = '0';

        /* now, check all the 4 neighboring cells again recursively, marking them
        as we go by same dfs approach
        */

        dfs(map, i-1, j);
        dfs(map, i+1, j);
        dfs(map, i, j+1);
        dfs(map, i, j-1);

        /* at this stage, all neighboring cells have been either visited, or water,
        making this cell an island.
        */
        return 1;
    }
}
