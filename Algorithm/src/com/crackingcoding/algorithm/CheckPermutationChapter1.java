package com.crackingcoding.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*Check Permutation: Given two strings, write a method to decide if one is a permutation of the other. 
*/
public class CheckPermutationChapter1 {

	// Two strings are a permutation of one another if the count of each character
	// is same in both the string
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

	private static String sort(String s) {
		char[] content = s.toCharArray();
		Arrays.sort(content);
		return content.toString();
	}

	// Check Permutation after Sorting
	private static boolean checkPermutation(String value1, String value2) {

		List<Character> characterList1 = new ArrayList<>();
		List<Character> characterList2 = new ArrayList<>();

		for (Character character : value1.toCharArray()) {
			characterList1.add(character);
		}

		for (Character character : value2.toCharArray()) {
			characterList2.add(character);
		}
		Collections.sort(characterList1);
		Collections.sort(characterList2);

		return characterList1.toString().equals(characterList2.toString());
	}

	// Check Permutation with Sorting
	private static boolean checkPermutationwithSort(String value1, String value2) {

		return sort(value1).equals(sort(value2));
	}

	private static void readValues() {
		Scanner scanner = new Scanner(System.in);
		String value1;
		String value2;
		value1 = scanner.next();
		value2 = scanner.next();
		System.out.println(compareCount(value1, value2));
	}

	public static void main(String[] args) {
		readValues();
	}

}
