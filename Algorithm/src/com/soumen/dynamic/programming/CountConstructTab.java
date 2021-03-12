package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.List;

public class CountConstructTab {
	
	public static int countConstruct(String target , List<String> inputArray) {
		int[] tableArray = new int[target.length() +1];
		tableArray[0] = 1;
		for(int i=0; i<target.length() ;i++) {
			if(tableArray[i] >0) {
				for(String s :inputArray) {
					if(target.startsWith(s,i) && i+s.length() < tableArray.length) {
							tableArray[i+s.length()]+= tableArray[i];	
					}
				}
			}
			
		}		
		return tableArray[target.length()];		
	}

	public static void main(String[] args) {
		System.out.println(countConstruct("purple", Arrays.asList("purp", "p", "ur","le","purpl")));
		System.out.println(countConstruct("skateboard", Arrays.asList("bo", "rd", "ate","t","ska","sk","boar")));
		System.out.println(countConstruct("abcdef", Arrays.asList("ab", "abc", "cd","def","abcd","ef","c")));
		System.out.println(countConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa","aaaa","aaaaa","aaaaaa")));

	}

}
