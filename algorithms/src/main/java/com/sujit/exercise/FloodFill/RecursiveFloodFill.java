package com.sujit.exercise.FloodFill;

public class RecursiveFloodFill {
    private int[][] image;

    public RecursiveFloodFill(int[][] image) {
        this.image = image;
    }

    public void changeColor(int sr, int sc, int newColor){
        int oldColor = image[sr][sc];
        if(oldColor == newColor){
            return;
        }
        fillNewColor(sr, sc, oldColor, newColor);
    }

    private void fillNewColor(int x, int y, int oldColor, int newColor) {
        if(x < 0 || y < 0 || x > image.length-1 || y > image[0].length-1){
            return;
        }
        if(image[x][y]!=oldColor){
            return;
        }
        image[x][y] = newColor;
        fillNewColor(x+1,y,oldColor, newColor);
        fillNewColor(x-1,y,oldColor, newColor);
        fillNewColor(x,y+1,oldColor, newColor);
        fillNewColor(x,y-1,oldColor, newColor);
    }

}
