package com.crackingcoding.algorithm;

import java.util.Scanner;

public class StringRotationChapter1 {
	
	public static boolean checkS2RotationofS1(String s1, String s2) {
		
		for (int i=0; i<s2.length() ;i++) {
			if(s1.charAt(0)== s2.charAt(i)) {
				return checkRotation(s1,s2,i);
			}
			
		}
		return false;
		
	}
	
	private static boolean checkRotation(String s1, String s2, int index) {
		StringBuilder sb = new StringBuilder();
		
		int j=0;
		for(int i =index ; i <(s2.length() +index) ; i++) {
			
			j = index;
			if(index > s2.length()-1) {
				j= index-s2.length();
			}
			sb.append(s2.charAt(j));
		}		
		
		return s2.toLowerCase().contains(s1.toLowerCase());
	}

	public static void readData() {
		Scanner scanner = new Scanner(System.in);		
		String mainString = scanner.nextLine();
		String modifiedString = scanner.nextLine();
		System.out.println(checkS2RotationofS1(mainString,modifiedString));
	}
	
	public static void main( String args[]) {
		readData();
	}


}
