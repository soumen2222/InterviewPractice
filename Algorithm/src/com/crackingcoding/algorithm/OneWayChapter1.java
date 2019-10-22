package com.crackingcoding.algorithm;

import java.util.Scanner;

public class OneWayChapter1 {

	public static void readString() {
		Scanner scanner = new Scanner(System.in);
		String input1 = scanner.next();
		String input2 = scanner.next();
		System.out.println(checkoneOperation(input1, input2));
	}

	private static boolean checkoneOperation(String input1, String input2) {

		if (input1.length() == input2.length()) {
			// Only Replace is possible
			int replaceCount = 0;

			for (int i = 0; i < input1.length(); i++) {
				if (input1.charAt(i) != input2.charAt(i)) {
					replaceCount++;
				}
			}
			return replaceCount <= 1;
		} else {
			// possible operation is add

			if (input1.length() > input2.length()) {
				return addOperation(input1, input2);
			} else {
				return addOperation(input2, input1);
			}
		}

	}

	private static boolean addOperation(String bigString, String smallString) {
		// Small string needs to add to match up with big string
		int addCount = 0;
		for (int input1Loc = 0, input2Loc = 0; input1Loc < bigString.length(); input1Loc++) {
			if (bigString.charAt(input1Loc) != smallString.charAt(input2Loc)) {
				addCount++;
			} else {
				if(smallString.length()-1>input2Loc) {
					input2Loc++;	
				}
			}
		}
		return addCount <= 1;
	}

	public static void main(String[] args) {
		readString();
	}

}
