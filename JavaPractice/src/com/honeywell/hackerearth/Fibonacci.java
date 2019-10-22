package com.honeywell.hackerearth;

public class Fibonacci {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int n = 20;
		int a=0;
		int b=0;
		int c=1;
		
		
		for (int i = 0; i < n; i++) {
			
			a = b;
			b= c;
			c = a+b;
			System.out.print(a + "  ");
			
		}

	}

}
