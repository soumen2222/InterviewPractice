package com.crackingcoding.algorithm;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

/*Is Unique: Implement an algorithm to determine 
if a string has all unique characters. What if you cannot use additional data structures? */
		
public class IsUniqueChapter1 {
	
	
	// 1st Approach - Sort the numbers and then check if the nearby elements are matching.
	
	public static boolean checkUniquebySorting(String number) {

		char[] characters = number.toCharArray();
		Arrays.sort(characters);

		for (int i = 0; i < characters.length-1; i++) {
			if (characters[i] == characters[i + 1]) {
				return false;
			}
		}
		return true;

	}
	
	
	//2nd Approach - Store the number in the integer i.e 256 numbers integer 1 is a , integer 2 is b ..
	
	public static boolean checkUniquebybitmanipulation(String number) {

		int checker = 0;
		char[] characters = number.toCharArray();
		for (int i = 0; i < characters.length; i++) {

			int value = characters[i] - 'a';

			if ((checker & ( 1<<value))>0) {
				return false;
			} else {
				checker = checker | 1<<value;
			}
		}
		return true;
	}
	
	public static boolean checkUniquebybitset(String number) {
		BitSet bits1 = new BitSet(256);
		char[] characters = number.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			int value = characters[i];
			if (bits1.get(value)) {
				return false;
			} else {
				bits1.set(value);
			}
		}
		return true;
	}
	
	public static void readString()
	{
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		System.out.println(checkUniquebybitset(input));
	}
	
	public static void main(String[] args)
	{
		readString();
	}

}
