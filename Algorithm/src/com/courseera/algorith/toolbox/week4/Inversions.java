package com.courseera.algorith.toolbox.week4;

import java.util.*;

public class Inversions {

private static long merge(int A[ ] , int start, int mid, int end) {
	    long count = 0;
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
		return count;
		}
	
    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave+1, right);
        //write your code here        
        numberOfInversions = merge(a ,left,ave,right);
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
        System.out.println(getNumberOfInversions(a, b, 0, a.length-1));
    }
}

