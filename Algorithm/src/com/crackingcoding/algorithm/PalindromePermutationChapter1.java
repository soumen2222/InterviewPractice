package com.crackingcoding.algorithm;

import java.util.Scanner;

/*Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome. 
A palindrome is a word or phrase that is the same forwards and backwards.
A permutation is a rearrangement of letters.
The palindrome does not need to be limited to just dictionary words.*/ 

/*Only check for small letters . No letters should be discarded. 
  Character.getNumericValue('z') method can be used */

public class PalindromePermutationChapter1 {
	
	// Option 1 - When the string has only characters
	public static boolean isPalindrome(String value)
	{
		int[] counts = new int[256];		
		char[] charcaters = value.toCharArray();
		
		for (char c : charcaters) {			
			counts[c]++;
		}
		
		// now check if there is more than one odd characters
		int oddcount =0;
		for(int i=0; i<counts.length ; i++) {
			if(counts[i]%2 !=0) {
				oddcount++;
			}
			if(oddcount>1) {
				return false;
			}
		}
		
		return true;
		
	}
	
	// With Bit manipulation
	public static boolean isPermtationofPalindrome(String phrase) {		
		int bitVector = createBitVector(phrase);
		return bitVector==0 ||exactlyOneBitSet(bitVector);
	}
	
	
	/* It will find if a bit vector has only one bit set.
	Picture an integer like 00010000. We could of course shift the integer repeatedly to check that there's only a single 1.
	Alternatively, if we subtract 1 from the number, we'll get 00001111. What's notable about this 
	is that there is no overlap between the numbers (as opposed to say 00101000, which, when we subtract 1 from, we get 00100111.)
	So, we can check to see that a number has exactly one 1 because if we subtract 1 from it and then AND it with the new number,
     we should get 0. 00010000 - 1 = 00001111 00010000 & 00001111 = 0 */
	 
	private static boolean exactlyOneBitSet(int bitVector) {
		return ((bitVector & (bitVector-1)) ==0);
	}

	private static int createBitVector(String phrase) {
		int bitVector =0;
		for(char c : phrase.toCharArray()) {
			int x = getCharNumber(c);
			bitVector = toggle(bitVector,x);
		}		
		return bitVector;
	}

	private static int toggle(int bitVector, int x) {		
		return bitVector ^ (1<<x);
	}

	private static int getCharNumber(char c) {
		int a= Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		int val = Character.getNumericValue(c);
		if( a<=val && z>=val) {
			return val -a;
		}
		
		return -1;
	}

	public static void read()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String inputString = scanner.nextLine();
		System.out.println(isPermtationofPalindrome(inputString.toLowerCase()));
	}
	
	public static void main (String[] args)
	{
		read();
	}

}
