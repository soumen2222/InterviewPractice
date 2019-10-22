package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Rise_of_the_weird {

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
	
       	List<Integer> sorted = sort(input);
       	List<Integer> Zombies = new ArrayList<Integer>();	
       	List<Integer> Vampires = new ArrayList<Integer>();	  
       	
       	for (int i = 0; i < sorted.size(); i++) {
        	       	if( sorted.get(i) % 2 ==0)
        	       	{
        	       		//even number
        	       		Zombies.add(sorted.get(i));
        	       	}
        	       	else{
        	       		Vampires.add(sorted.get(i));
        	       	}
        }
		
       	// do the sum
       	int Zombiessum = 0;
       	for (int i = 0; i < Zombies.size(); i++) {
       		
       		Zombiessum = Zombiessum + Zombies.get(i);
       		System.out.print(Zombies.get(i)+ " ");
       	}
       	Zombies.add(Zombiessum);
       	System.out.print(Zombies.get(Zombies.size()-1)+ " ");
       	
       	
       	
    	int Vampiressum = 0;
       	for (int i = 0; i < Vampires.size(); i++) {
       		
       		Vampiressum = Vampiressum + Vampires.get(i);
       		System.out.print(Vampires.get(i)+ " ");
       	}
       	Vampires.add(Vampiressum);	
       	System.out.print(Vampires.get(Vampires.size()-1)+ " ");  	
       	
		
	}

}
