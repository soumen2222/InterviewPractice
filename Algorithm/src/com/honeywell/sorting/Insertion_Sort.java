package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Insertion_Sort {

	// minimum first
	private static List<Integer> sort(List<Integer> input) {
		for (int i = 0; i < input.size(); i++) {
			
			int temp = input.get(i);
			int j = i;
		     
			while ( j>0 && temp < input.get(j-1)){
				
				input.set(j, input.get(j-1));
				j--;

			}
			
			input.set(j, temp);
		}


		return input;

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String temp[] = line.split(" ");
        int N = Integer.parseInt(temp[0]);      
        
        String str = br.readLine();
        String temp1[] = str.split(" ");
        List<Integer> input = new ArrayList<Integer>();	
        for (int i = 0; i < N; i++) {
        	input.add(Integer.parseInt(temp1[i]));
        }
	
		
		List<Integer> output = sort(input);
		
		
		
		for (Integer integer : output) {			
			System.out.print(integer + " ");
			
		}
	}

}