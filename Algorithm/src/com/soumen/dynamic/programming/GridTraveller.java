package com.soumen.dynamic.programming;

public class GridTraveller {
	
	//Complexity- o(n*m) Space o(n*m)
	public static long gridTraveller(int m , int n) {
		
		long[][] array = new long[m+1][n+1];
		//Initilaise the array
		for(int i=0; i<=m ;i++) {
			for(int j=0; j<=n ;j++) {
				array[i][j]=0;
			}
		}
		
		array[1][1] =1;
		
		for(int i=0; i<=m ;i++) {
			for(int j=0; j<=n ;j++) {
				long value = array[i][j];
				if(i+1 <=m) array[i+1][j]+=value;
				if(j+1 <=n) array[i][j+1]+=value;
			}
		}
		
		return array[m][n];
		
	}
	
	public static void main(String[] args) {
		
	System.out.println(gridTraveller(1, 1));
	System.out.println(gridTraveller(2, 3));
	System.out.println(gridTraveller(3, 2));
	System.out.println(gridTraveller(3, 3));
	System.out.println(gridTraveller(18, 18));
		
	}


}
