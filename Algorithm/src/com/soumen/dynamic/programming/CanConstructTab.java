package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.List;

public class CanConstructTab {
	
	public static boolean canConstruct(String target , List<String> inputArray) {
		boolean[] tableArray = new boolean[target.length() +1];
		tableArray[0] = true;
		for(int i=0; i<target.length() ;i++) {
			if(tableArray[i]) {
				for(String s :inputArray) {
					if(target.startsWith(s,i) && i+s.length() < tableArray.length) {
							tableArray[i+s.length()] = true;	
					}
				}
			}
			
		}		
		return tableArray[target.length()];		
	}

	public static void main(String[] args) {
		System.out.println(canConstruct("purple", Arrays.asList("purp", "p", "ur","le","purpl")));
		System.out.println(canConstruct("skateboard", Arrays.asList("bo", "rd", "ate","t","ska","sk","boar")));
		System.out.println(canConstruct("abcdef", Arrays.asList("ab", "abc", "cd","def","abcd","ef","c")));
		System.out.println(canConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa","aaaa","aaaaa","aaaaaa")));

	}

}
