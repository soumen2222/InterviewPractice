package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Merge_Sort_Matchmakers {

	private static void mergelow(int A[ ] , int start, int mid, int end) {
		
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
	
	
private static void mergehigh(int A[ ] , int start, int mid, int end) {
		
		int p = start ,q = mid+1;
		int Arr[] = new int[end-start+1];
		int k=0;

		for(int i = start ;i <= end ;i++) {
		    if(p > mid)      //checks if first part comes to an end or not .
		       Arr[ k++ ] = A[ q++] ;

		   else if ( q > end)   //checks if second part comes to an end or not
		       Arr[ k++ ] = A[ p++ ];

		   else if( A[ p ] > A[ q ]) 
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
	
	
	private static void merge_sort(int A[ ] , int start , int end ,boolean flag)
	   {
	           if( start < end ) {
	           int mid = (start + end ) / 2 ;           // defines the current array in 2 parts .
	           merge_sort (A, start , mid,flag) ;                 // sort the 1st part of array .
	           merge_sort (A,mid+1 , end ,flag) ;              // sort the 2nd part of array.

	         // merge the both parts by comparing elements of both the parts.
	           if(flag)
	          mergelow(A,start , mid , end );  
	           else
	        	   mergehigh(A,start , mid , end );  
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

			// Get nos of boys and girls
			int N = Integer.parseInt(temp2[0]);			

			// Get all the ages first giles then boys
			
				String line3 = br.readLine();
				String temp3[] = line3.split(" ");			
			
			    int[] girlsAge = new int[N];
			    int[] boysAge = new int[N];
			
			  for (int k = 0; k < N; k++) {
				
					girlsAge[k] = Integer.parseInt(temp3[k]);
					
				}
			
			
			String line4 = br.readLine();
			String temp4[] = line4.split(" ");			
		
			for (int j = 0; j < N; j++) {
				boysAge[j] = Integer.parseInt(temp4[j]);
			}

			// logic
			calculate(girlsAge,boysAge,N);

		}

	}

	private static void calculate(int[] girlsAge,int[] boysAge, int n) {
		
		// Step 1: Sort the input array numbers, lowest first.
		
		merge_sort(girlsAge,0,n-1,true);
		merge_sort(boysAge,0,n-1,false);
		int count=0;
		for (int j = 0; j < n; j++)
		{
			if((girlsAge[j]%boysAge[j]==0) || (boysAge[j]%girlsAge[j]==0) )
				count++;
		}
			
		System.out.println(count);		
	}


	
}
