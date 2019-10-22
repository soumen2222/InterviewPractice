package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Merge_Sort_PebblesGame {

	private static void merge(int A[ ] , int start, int mid, int end) {
		
		int p = start ,q = mid+1;
		int Arr[] = new int[end-start+1];
		int k=0;

		for(int i = start ;i <= end ;i++) {
		    if(p > mid)      //checks if first part comes to an end or not .
		       Arr[ k++ ] = A[ q++] ;

		   else if ( q > end)   //checks if second part comes to an end or not
		       Arr[ k++ ] = A[ p++ ];

		   else if( A[ p ] < A[ q ]) 
		   {//checks which part has smaller element.
			  
		      Arr[ k++ ] = A[ p++ ];		      
		   }
		   else{
			  
		      Arr[ k++ ] = A[ q++];
		     
		   }
		 }
		  for (int a=0 ; a< k ;a ++) {
		   /* Now the real array has elements in sorted manner including both 
		        parts.*/
		     A[ start++ ] = Arr[ a ] ;                          
		  }
		}
	
	
	private static void merge_sort (int A[ ] , int start , int end )
	   {
	           if( start < end ) {
	           int mid = (start + end ) / 2 ;           // defines the current array in 2 parts .
	           merge_sort (A, start , mid ) ;                 // sort the 1st part of array .
	           merge_sort (A,mid+1 , end ) ;              // sort the 2nd part of array.

	         // merge the both parts by comparing elements of both the parts.
	          merge(A,start , mid , end );   
	   }                    
	}
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line1 = br.readLine();
		String temp[] = line1.split(" ");

		// Get nos of test cases
		int testCases = Integer.parseInt(temp[0]);

		for (int i = 0; i < testCases; i++) {

			String line2 = br.readLine();
			String temp2[] = line2.split(" ");

			// Get nos of Pebbles and cost of unit.
			int N = Integer.parseInt(temp2[0]);
			int M = Integer.parseInt(temp2[1]);

			// Get all the X co-ordinate
			int[] Input = new int[N];
			String line3 = br.readLine();
			String temp3[] = line3.split(" ");

			for (int j = 0; j < N; j++) {
				Input[j] = Integer.parseInt(temp3[j]);
			}

			// logic
			calculate(Input,N,M);

		}

	}

	private static void calculate(int[] input,int size, int m) {
		
		// Step 1: Sort the input array numbers, lowest first.
		
		merge_sort(input,0,size-1);
		double sum =0;	
		int max1 = input[(size-1)];
		int max2 = input[(size-2)];
		int min1 = input[0];
		int min2 = input[1];
		sum = 2 * ( max1-min2) + 2 * (max2-min1) +shortDist(min1,min1,min2,min2) + shortDist(max2,max2,max1,max1);		
		System.out.println(m*(long)(Math.ceil(sum)));		
	}


	private static double shortDist(long x1, long y1, long x2, long y2) {				
		
		long xval = (x2-x1)*(x2-x1);
		long yval = (y2-y1) * (y2-y1);
		double dist = Math.sqrt(xval + yval);	
		return dist;
		
	}


	
}
