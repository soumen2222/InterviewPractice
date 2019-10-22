package com.crackingcoding.algorithm;

import java.util.Scanner;

/*
Given a smaller strings and a bigger string b, 
design an algorithm to find all permutations of the shorter string within the longer one. Print the location of each permutation. */


public class SmallerBigPermutation {

	
private static boolean compareCount(String value1, String value2) {		
		
		if(value1.length()!=value2.length())
		{
			return false;
		}
		// Only ASCII Characters
		int[] stringLength = new int[256];

		for (Character character : value1.toCharArray()) {
			++stringLength[character];
		}

		for (Character character : value2.toCharArray()) {
			if (--stringLength[character] < 0) {
				return false;
			}

		}
		return true;

	}
	
private static void permShorterinLargerString(String largeString, String smallString) {
	
	for(int i=0 ; i <=(largeString.length()-smallString.length()) ; i++ )
	{
		String perm = largeString.substring(i, smallString.length()+i);
		if(compareCount(perm ,smallString))
		{
			System.out.println("String: " + perm+  " at Index: " + i);
		}
	}
	
	
}
	private static void readValues() {
		Scanner scanner = new Scanner(System.in);
		String value1;
		String value2;
		value1 = scanner.next();
		value2 = scanner.next();
		permShorterinLargerString(value1, value2);
	}

	public static void main(String[] args) {
		readValues();
	}
}
