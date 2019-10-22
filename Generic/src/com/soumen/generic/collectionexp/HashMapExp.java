package com.soumen.generic.collectionexp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class HashMapExp {
	
	public static void main(String args[]) {
		
		String[] words = {"Helo", "World", "Hel", "Worl1d", "World"};
		HashMap<String, Integer> lnkh = new LinkedHashMap<>();
		
		for (int i = 0; i < words.length; i++) {
			
			if(lnkh.containsKey(words[i]))
				{
				   // then iterate the key value;
				   lnkh.put(words[i], lnkh.get(words[i]) + 1);				
				}
			else
			{
				lnkh.put(words[i], 1);
			}
		}
		
		//loop through and find out the the key whose value is greater than 1.
		
		String output = null;		
		for (Entry<String, Integer> entry :lnkh.entrySet()) {			
			if(entry.getValue()>1){
				output = entry.getKey();
				break;
			}
			
		}
		
		System.out.println(output);
		
		
	}
	


}
