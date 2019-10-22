package com.soumen.coding.challenge;

import java.util.Scanner;

public class Arrays_LeftRotation {
	
	
	    public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int n = in.nextInt();
	        int k = in.nextInt();
	        int a[] = new int[n];
	        for(int a_i=0; a_i < n; a_i++){
	            a[a_i] = in.nextInt();
	        }
	      
	       // arrayLeftRotation(a, n, k);
	        rotateArray(a,k);
	        for(int i = 0; i < n; i++)
	            System.out.print(a[i] + " ");
	      
	        System.out.println();
	      
	    }
	private static void arrayLeftRotation(int[] input ,int n, int d) {
		
		for (int i = 0; i < d; i++) 
		{
			 int var = input[0];
			 for (int j = 0; j < input.length -1; j++) 
	           {
	        	   input[j] = input[j+1] ;     	
	           }
			 input[input.length -1] = var;
			 
			
		}
		
	}
	
	public static int[] rotateArray(int[] arr, int d){
	    // Because the constraints state d < n, we need not concern ourselves with shifting > n units.
	    int n = arr.length;

	    // Create new array for rotated elements:
	    int[] rotated = new int[n]; 

	    // Copy segments of shifted elements to rotated array:
	    System.arraycopy(arr, d, rotated, 0, n - d);
	    System.arraycopy(arr, 0, rotated, n - d, d);

	    return rotated;
	}

	

}
