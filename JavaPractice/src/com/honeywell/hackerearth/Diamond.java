package com.honeywell.hackerearth;

public class Diamond {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//Size of matrix as odd number
		int n =35;
		
//middle element of array is 
		int mid = n/2 +1;
		for (int i = 1; i <= n; i++) {
			
			int x= mid +(i-1);
			int y = mid - (i-1);
			
			if (x>n)
			{
				x =Math.abs(Math.abs(x)-(n-1));
			}
			
			if (y<1)
			{
				y =Math.abs(Math.abs(y)-(n-1));
			}
			 
		for (int j = 1; j <=n; j++) {
			System.out.print(" ");
			if(( j==x) || (j==y))
			{
				System.out.print("*");
			}
			 
			
		}
		System.out.println("");
		}
		
		
	}

}
