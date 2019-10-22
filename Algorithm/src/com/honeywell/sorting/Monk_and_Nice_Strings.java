package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Monk_and_Nice_Strings {

	// Lex Sort
	private static void process(List<String> input) {
		for (int i = 0; i < input.size(); i++) {
			
			String temp = input.get(i);
			int j = i;
			int count =0;
		     
			while ( j>0 ){				
				
				j--;
				if(temp.compareTo(input.get(j)) > 0)
				count++;
			}
			
			System.out.println(count);
		}

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String temp[] = line.split(" ");
        int N = Integer.parseInt(temp[0]);      
        
        
       
        List<String> input = new ArrayList<String>();	       
        for (int i = 0; i < N; i++) {
        	String str = br.readLine();
        	 String temp1[] = str.split(" ");
        	 input.add(temp1[0]);        	 
        }
	
       
		 process(input);
		
		
	}

}
