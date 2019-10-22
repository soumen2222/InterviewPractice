package com.crackingcoding.algorithm;

import java.util.Scanner;

/*URLify: Write a method to replace all spaces in a string with '%20'. 
You may assume that the string has sufficient space at the end to hold the additional characters,
and that you are given the "true" length of the string. 
(Note: If implementing in Java, please use a character array so that you can perform this operation in place.) */

public class URLifyChapter1 {

	public static String URLify(String inputString) {

		char[] characters = inputString.toCharArray();

		StringBuilder outputString = new StringBuilder();
		
		
		for (int i = 0; i < characters.length; i++) {
			if (characters[i] == ' ') {
				outputString.append("%20");
			}else {
			outputString.append(characters[i]);}
		}

		return outputString.toString();
	}

	public static void readData() {
		Scanner scanner = new Scanner(System.in);		
		String value = scanner.nextLine();
		System.out.println(URLify(value));
	}
	
	public static void main( String args[]) {
		readData();
	}

}
