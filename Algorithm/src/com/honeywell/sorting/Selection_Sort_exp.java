package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Selection_Sort_exp {

	private static List<Integer> input1 = new ArrayList<Integer>();	

// Selection Sort , reduce the effective size of array by 1 in each iteration.
private static List<Integer> sort(List<Integer> input, int x)
{		int minimum;  

		for (int i=0; i<x ;i++) {	
			
			minimum = i ;
			
			for (int j=i+1; j<input.size() ;j++) {
				
				if (input.get(minimum) > input.get(j))
				{
					
					minimum = j;
					
				}				
			}
			
			swap ( input, minimum, i) ;
			
		}
		
				
	   return input;
	
}
	
	
	
	
	private static void swap(List<Integer> input, Integer minimum, Integer position) {
	
		Integer Var1 = input.get(minimum);
		Integer Var2 = input.get(position);					
		input.set(position, Var1);	
		input.set(minimum, Var2);	
}




	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String temp[] = line.split(" ");
        int N = Integer.parseInt(temp[0]);
        int x = Integer.parseInt(temp[1]);
        
        String str = br.readLine();
        String temp1[] = str.split(" ");
        
        for (int i = 0; i < N; i++) {
        	input1.add(Integer.parseInt(temp1[i]));
        }
	
		
		List<Integer> var = sort(input1,x);
		
		for (Integer integer : var) {			
			System.out.print(integer + " ");
			
		}
	}

}
