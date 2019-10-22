package com.soumen.coding.challenge;

import java.util.*;

public class SplitMilk {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int t = Integer.parseInt(sc.nextLine());
        for (int n = 0; n < t; n++) {        
        	int glassNo = Integer.parseInt(sc.nextLine());        	
            String[] maxGlass=sc.nextLine().split(" ");
            String[] initialGlass=sc.nextLine().split(" ");            
            int[] maxGl = new int[glassNo];
        	int[] intGl = new int[glassNo];
            for (int i = 0; i < glassNo; i++){
            	maxGl[i]=Integer.parseInt(maxGlass[i]);
            	intGl[i]=Integer.parseInt(initialGlass[i]); 
            }
            process(maxGl,intGl);
            
        }

    }
    
    private static void process(int[] maxGlass , int[] intGlass){
    	
    	int vSplit=0;
    	 for (int i = 0; i < maxGlass.length-1; i++){
    		 int val= (intGlass[i+1]+ intGlass[i] - maxGlass[i+1]);
    		 intGlass[i+1]=Math.min(intGlass[i+1]+intGlass[i],maxGlass[i+1]);
    		 if(val>0){
    	       	 vSplit= vSplit + val;
    		 }
    	 }       	 
         System.out.println(Math.min(intGlass[maxGlass.length-1]+vSplit, maxGlass[maxGlass.length-1]) +" " + vSplit);       	 
        }
}