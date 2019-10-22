package com.courseera.algorith.toolbox.week4;


import java.util.*;

public class InversionsModified {
	private static long count = 0;
	
	private static void merge(int A[ ] , int start, int mid, int end) {
		
		int p = start ,q = mid+1;
		int Arr[] = new int[end-start+1];
		int k=0;

		for(int i = start ;i <= end ;i++) {
		    if(p > mid)      //checks if first part comes to an end or not .
		       Arr[ k++ ] = A[ q++] ;

		   else if ( q > end)   //checks if second part comes to an end or not
		       Arr[ k++ ] = A[ p++ ];

		   else if( A[ p ] <= A[ q ]) {	 
			   Arr[ k++ ] = A[ p++ ];	
		   }
		   else{			   
		      Arr[ k++ ] = A[ q++];
		  	  count = count + 1 + (mid - p);		     
		   }
		 }
		  for (int a=0 ; a< k ;a ++) {
		      A[ start++ ] = Arr[ a ] ;                          
		  }
		}
	
	
	private static void merge_sort (int A[ ] , int start , int end )
	   {
	           if( start < end ) {
	           int mid = (start + end ) / 2 ;           
	           merge_sort (A, start , mid ) ;                 // sort the 1st part of array .
	           merge_sort (A,mid+1 , end ) ;                 // sort the 2nd part of array.
	           merge(A,start , mid , end );   
	   }                    
	}
    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave, right);
        //write your code here
        return numberOfInversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        
        merge_sort(a, 0, n - 1);

		System.out.print(count);
       // System.out.println(getNumberOfInversions(a, b, 0, a.length));
    }
}

