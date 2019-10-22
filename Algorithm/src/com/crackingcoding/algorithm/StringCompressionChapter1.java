package com.crackingcoding.algorithm;

import java.util.Scanner;

public class StringCompressionChapter1 {	
	
	public static String doCompression(String unCompressedString) {
		int count=0;
		StringBuilder compressedString = new StringBuilder();
		for (int startIndex =0, currentIndex=0; currentIndex < unCompressedString.length() ;currentIndex++) {			
			if(unCompressedString.charAt(startIndex)== unCompressedString.charAt(currentIndex)) {
				count++;
			}else {
				compressedString.append(unCompressedString.charAt(startIndex)).append(count);
				startIndex=currentIndex;
				count=1;
			}
			if(currentIndex==unCompressedString.length()-1 ) {
				compressedString.append(unCompressedString.charAt(startIndex)).append(count);
			}
		}		
		return (unCompressedString.length() <=compressedString.length()) ? unCompressedString : compressedString.toString() ;
	}
	
	
	
	public static void main(String[] args) {
		readData();
	}
	

	public static void readData() {
		Scanner scanner = new Scanner(System.in);
		String unCompressedString = scanner.next();		
		System.out.println(doCompression(unCompressedString));
	}

}
