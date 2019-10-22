package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Selection_Sort {

	private static List<Integer> input1 = new ArrayList<Integer>();	

// Lowest first
private static List<Integer> sort(List<Integer> input)
{		int minimum;  

		for (int i=0; i<input.size() ;i++) {	
			
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
        int N = Integer.parseInt(line);
        
        String str = br.readLine();
		String temp[] = str.split(" ");
        
        for (int i = 0; i < N; i++) {
        	input1.add(Integer.parseInt(temp[i]));
        }
	
		
		List<Integer> var = sort(input1);
		
		for (Integer integer : var) {			
			System.out.print(integer + " ");
			
		}
	}

}
