package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Old_keypad {

	private static List<Integer> input1 = new ArrayList<Integer>();	
	private static List<Integer> input2 = new ArrayList<Integer>();	

// Highest first
private static List<Integer> sortHighest(List<Integer> input)
{		int minimum;  

		for (int i=0; i<input.size() ;i++) {	
			
			minimum = i ;
			
			for (int j=i+1; j<input.size() ;j++) {
				
				if (input.get(minimum) < input.get(j))
				{
					
					minimum = j;
					
				}				
			}
			
			swap ( input, minimum, i) ;
			
		}
		
				
	   return input;
	
}
	

//Lowest first
private static List<Integer> sortLowest(List<Integer> input)
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
       int output = 0;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int frequencies = Integer.parseInt(line);
        
        String str = br.readLine();
		String temp[] = str.split(" ");
        
        for (int i = 0; i < frequencies; i++) {
        	input1.add(Integer.parseInt(temp[i]));
        }
	
        
        String line2 = br.readLine();
        int keys = Integer.parseInt(line2);
        
        String str2 = br.readLine();
		String temp2[] = str2.split(" ");
        
        for (int i = 0; i < keys; i++) {
        	input2.add(Integer.parseInt(temp2[i]));
        }
		
		List<Integer> sortedfreq = sortHighest(input1);
		
		List<Integer> numbers = new ArrayList<Integer>();	
		
		for(int a =0; a <input2.size() ; a++ )
		{
			for(int b =1; b <=input2.get(a) ; b++ )
				
				numbers.add(b);
		}
		
		List<Integer> sortednumbers = sortLowest(numbers);	
		
		if(sortednumbers.size()< sortedfreq.size())
		{
			System.out.print(-1);
		}
		
		int size = Math.min(sortednumbers.size(), sortedfreq.size());
		
		for(int i =0; i< size ; i++){
			
			output = output + (sortedfreq.get(i)*sortednumbers.get(i));
		}
		
		
				
	  System.out.print(output);
		
	}

}
