package com.crackingcoding.algorithm;

import java.util.ArrayList;
import java.util.List;
/*
Design an algorithm to print all permutations of a string. For simplicity, assume all characters are unique. 
*/
public class StringPermutation {

	public static int count = 0;
	/*public static void permute(String soFar, String strLeft) {

	    if(strLeft.length() == 1) {
	        soFar += strLeft;
	        System.out.println(++count + " :: " + soFar);
	    }

	    for(int i=0; i<strLeft.length(); i++) {
	        permute(soFar + strLeft.charAt(i), strLeft.substring(0,i) + strLeft.substring(i+1));
	    }
	}
*/
	
	public static List<String> allPermutation(String Value) {
		char[] chars = Value.toCharArray();
		List<String> mergedList = new ArrayList<>();
		for (char c : chars) {
			mergedList = IterativePerm(mergedList, c);
		}
		return mergedList;

	}

	private static List<String> IterativePerm(List<String> mergedList, char c) {

		List<String> output = new ArrayList<>();

		if (mergedList.isEmpty()) {
			output.add(Character.toString(c));
		}
			for (String string : mergedList) {

				for (int i = 0; i <= string.length(); i++) {
					StringBuilder str = new StringBuilder(string);
					str.insert(i, c);
					output.add(str.toString());
				}

			}	

		return output;
	}

	public static void main(String args[]) {

		List<String> output = allPermutation("abc");
		//permute("", "ab");
		System.out.println(output.size());
		for (String string : output) {
			System.out.println(string);
		}
	}

}
