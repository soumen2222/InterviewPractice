package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllConstructTab {
	
	public static List<List<String>> allConstruct(String target , List<String> inputArray) {
		 List<List<List<String>>> tableArray = new ArrayList<>();
	    for(int i=0; i<=target.length();i++) {
	    	tableArray.add(new ArrayList<>());
	    }
	    List<String> init = new ArrayList<>();
	    tableArray.get(0).add(init);
	    
		for(int i=0; i<target.length() ;i++) {
			if(!tableArray.get(i).isEmpty()) {
				for(String s :inputArray) {
					if(target.startsWith(s,i) && i+s.length() < tableArray.size()) {										
						List<List<String>> items = new ArrayList<>();
						tableArray.get(i).forEach(data -> {
							List<String> item = new ArrayList<>();
							item.addAll(data);
							item.add(s);
							items.add(item);
						});
						tableArray.get(i+s.length()).addAll(items);
					}
				}
			}
			
		}		
		return tableArray.get(target.length());		
	}

	public static void main(String[] args) {
		System.out.println(allConstruct("purple", Arrays.asList("purp", "p", "ur","le","purpl")));		
		System.out.println(allConstruct("skateboard", Arrays.asList("bo", "rd","ate","t","ska","sk","boar")));
		System.out.println(allConstruct("abcdef", Arrays.asList("ab", "abc", "cd","def","abcd","ef","c")));
		System.out.println(allConstruct("aaaaaaaaz", Arrays.asList("a", "aa","aaa","aaaa","aaaaa","aaaaaa")));
	

	}

}
