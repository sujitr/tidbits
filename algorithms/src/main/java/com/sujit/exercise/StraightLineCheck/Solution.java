package com.sujit.exercise.StraightLineCheck;

public class Solution {

    private int[][] coordinates;

    public Solution(int[][] coordinates) {
        this.coordinates = coordinates;
    }

    public boolean checkStraightLine() {
        // if there are only two points then return true, else check further
        for(int i = 2; i < coordinates.length; i++){
            if(!checkForThreePoints(coordinates[i],coordinates[0],coordinates[1])){
                return false;
            }
        }
        return true;
    }

    private boolean checkForThreePoints(int[] a, int[] b, int[] c){
        int x = a[0], x1 = b[0], x2 = c[0];
        int y = a[1], y1 = b[1], y2 = c[1];

        return (x1-x)*(y2-y1)==(y1-y)*(x2-x1);
    }
}
