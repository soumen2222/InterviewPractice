package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllConstruct {
	
	public static List<List<String>> allConstruct (String target, List<String> wordBank){
		//return utilAllConstruct1(target,wordBank);
		
		return allConstructMemo(target,wordBank,new HashMap<>());
	}
	
	public static List<List<String>> allConstructMemo (String target, List<String> wordBank, Map<String, List<List<String>>> memo ){
		if(memo.containsKey(target)) return memo.get(target);
		if(target.equals("")) {
		 List<List<String>> list1 = new ArrayList<>();
		 List<String> list2 = new ArrayList<>();
		 list1.add(list2);
		 return list1;
		}
	
		List<List<String>> result = new ArrayList<>();
		
		for(String word:wordBank) {
			if(target.startsWith(word)) {
				String suffix = target.substring(word.length());
				List<List<String>> suffixWays = allConstructMemo(suffix,wordBank,memo);
				List<List<String>> targetWays = new ArrayList<>();
				suffixWays.forEach(list -> {
					List<String> l = new ArrayList<>();
					 l.add(word);
					 l.addAll(list);
					 targetWays.add(l);
				});				
				result.addAll(targetWays);
			}
		}
		memo.put(target, result);
		return result;
	}
	
	public static List<List<String>> utilAllConstruct1 (String target, List<String> wordBank){
		if(target.equals("")) {
		 List<List<String>> list1 = new ArrayList<>();
		 List<String> list2 = new ArrayList<>();
		 list1.add(list2);
		 return list1;
		}
	
		List<List<String>> result = new ArrayList<>();
		
		for(String word:wordBank) {
			if(target.startsWith(word)) {
				String suffix = target.substring(word.length());
				List<List<String>> suffixWays = utilAllConstruct1(suffix,wordBank);
				List<List<String>> targetWays = new ArrayList<>();
				suffixWays.forEach(list -> {
					List<String> l = new ArrayList<>();
					 l.add(word);
					 l.addAll(list);
					 targetWays.add(l);
				});				
				result.addAll(targetWays);
			}
		}
		return result;
	}


	public static void main(String[] args) {
	
		System.out.println(allConstruct("purple", Arrays.asList("purp", "p", "ur","le","purpl")));
		System.out.println(allConstruct("abcdef", Arrays.asList("ab", "abc", "cd","def","abcd","ef","c")));
		System.out.println(allConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaz", Arrays.asList("a", "aa", "aaa","aaaa","aaaaa","aaaaaa")));
		
	}

}
