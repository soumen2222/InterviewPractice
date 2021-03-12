package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.List;

public class CanSum {
	
	public static boolean canSum(int targetSum , List<Integer> numArray) {
		boolean[] arrayTable = new boolean[targetSum+1];	
		arrayTable[0]=true;
		
		for(int i= 0; i<= targetSum; i++) {
			if(arrayTable[i]) {
				for(Integer num:numArray) {					
					if(i+num < arrayTable.length ) {
						arrayTable[i+num]= true;
					}				
				}
			}
			
		}		
		return arrayTable[targetSum];
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(canSum(7,Arrays.asList(5,3,4)));
		System.out.println(canSum(7,Arrays.asList(2,4)));
		System.out.println(canSum(8,Arrays.asList(2,3,5,4)));
		System.out.println(canSum(300,Arrays.asList(7,14)));
			
		}

}
