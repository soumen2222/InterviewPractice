package com.courseera.algorith.toolbox.week6;


import java.util.*;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
        //write you code here
      	  int[][] value = new int[W+1][w.length+1];
    	  
    	  for (int x=0;x<=W;x++)
    	  {
    		  value[x][0]=0;
    	  }
    	  for(int y =0; y<=w.length;y++)
    	  {
    		  value[0][y]=0;		  
    	  }
    	  
    	  for (int i =1; i<=w.length;i++)  // total number of items
    	  {
    		  for(int j= 1; j<=W;j++)
    		  {
    			  value[j][i]=value[j][i-1];
    			  if(w[i-1]<=j)
    			  {
    				  int val = value[j-w[i-1]][i-1] +w[i-1]  ; //get the value of ith item, here value is same as weight
    				
    				  if(value[j][i]<val)	
    				  {
    					  value[j][i]=val; 
    				  }
    			  }   			  
    		  }
    	  }
    	  
        return value[W][w.length];
       	
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}

