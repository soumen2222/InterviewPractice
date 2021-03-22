package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.HashMap;

public class PaintFill {
    enum Color{
        Black,Red,Green,White,Blue
    }

    public static boolean paintFill(Color[][] grid, int x,int y, Color oColor, Color nColor){
        if(x<0 || x > grid[0].length || y <0 || y > grid.length) return false;
        if(grid[y][x]==oColor){
            grid[y][x] = nColor;
            paintFill(grid,x-1,y,oColor,nColor);
            paintFill(grid,x+1,y,oColor,nColor);
            paintFill(grid,x,y+1,oColor,nColor);
            paintFill(grid,x,y-1,oColor,nColor);
        }
        return true;
    }

    public static boolean paintFill(Color[][] grid, int x,int y, Color nColor){
        return paintFill(grid,x,y,grid[y][x],nColor);
    }


    public static void main(String[] args){
        Color[][] grid = new Color[3][3];
        System.out.println("Filled:" + paintFill(grid,2,2,Color.Red));
    }
}
