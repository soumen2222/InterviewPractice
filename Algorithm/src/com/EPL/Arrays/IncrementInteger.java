package com.EPL.Arrays;

import java.util.ArrayList;
import java.util.List;

public class IncrementInteger {
	
	public static void operation(int[] number)
	{
		int dividend = 1;
		
		for(int i = 0 ; i<number.length;i++)
		{
			int tempnumber = (number[i] + dividend)% 10;
			dividend = (number[i] + dividend)/10;
			number[i] = tempnumber;
		}
	}
	
	
	
	public static List<Integer> plusOne (List<Integer> A) {
		int n = A.size() - 1;
		A.set(n, A.get(n) + 1);
		for (int i = n; i > 0 && A.get(i) == 10; --i) {
		A .set (i , 0) ;
		A.set(i - 1, A.get(i - 1) + 1);
		}
		if (A .get (0) == 10) {
		// Need additional digit as the most significant digit ( i.e A. get (9))
		// has a carry-out .
		A .set (0 ,0) ;
		A . add (0 , 1);
		}
		return A ;
		}

	public static void main(String[] args) {
		
		int[] number = {9,9,4};
		operation(number);
		for(int i = number.length-1 ; i>=0;i--)
		{
			System.out.print(number[i] + " ");
		}
		List<Integer> val = new ArrayList<>();
		val.add(1);
		val.add(9);
		val.add(2);
		List<Integer> output = plusOne(val);
		for (Integer integer : output) {
			System.out.print(integer);
		}
		
		

	}

}
