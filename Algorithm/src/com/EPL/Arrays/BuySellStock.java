package com.EPL.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BuySellStock {
	
	

	public static void main(String[] args) {
		List<Integer> val = new ArrayList<>(Arrays.asList(310,310,275,275,260,260,260,230,230,230));
		System.out.print(buySellOnce(val) + " " );
		
	}

	private static int buySellOnce(List<Integer> A) {
		
		Stack<String> p = new Stack<>();
		
		int buyPrice = A.get(0);
		int maxProfit = 0;
		int profit =0;
		for (int i = 1; i < A.size(); ++i) {
			
			profit = A.get(i) -buyPrice;
			
			if(profit >0)
			{
				maxProfit = Math.max(maxProfit, profit);
			}else
			{
				buyPrice = A.get(i);				
			}
		
			}
		
		return maxProfit;
	}

}
