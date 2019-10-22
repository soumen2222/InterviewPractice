package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Merge_Sort {
	
	private static void merge(int A[ ] , int start, int mid, int end) {
		 //stores the starting position of both parts in temporary variables.
		
		System.out.println("start:" + start + "mid:" + mid + "end:" + end);
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
			  System.out.println("Smaller Element:" +A[ p ] + " " + A[ q ] );
		      Arr[ k++ ] = A[ p++ ];		      
		   }
		   else{
			   System.out.println("Big Element:" +A[ p ] + " " + A[ q ] );
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
        String line = br.readLine();
        String temp[] = line.split(" ");
        int N = Integer.parseInt(temp[0]);      
        
        String str = br.readLine();
        String temp1[] = str.split(" ");
        int input[] = new int[N+1];
               
        for (int i = 0; i < N; i++) {
        	input[i] = Integer.parseInt(temp1[i]);        	
        }
	
        merge_sort(input,0,N-1);
        
      for (int i = 0; i < N; i++) {
        	System.out.print(input[i] + " ");        	     	
        }
      

	}

}
