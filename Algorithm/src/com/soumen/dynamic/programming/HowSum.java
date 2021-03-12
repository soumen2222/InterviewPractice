package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HowSum {

	public static List<Integer> howSum(int targetSum, List<Integer> dataArray ){
		List<List<Integer>> tableArray = new ArrayList<>();
		for(int i =0; i<=targetSum +1 ; i++) {
			tableArray.add(null);
		}
		tableArray.set(0, new ArrayList<>());		
		for(int i= 0; i<=targetSum ; i++) {			
			if(tableArray.get(i)!=null) {
				for(Integer num:dataArray) {
					if(i+num < tableArray.size()) {
						List<Integer> list = tableArray.get(i);
						List<Integer> finalList = new ArrayList<>();	
						finalList.add(num);
						finalList.addAll(list);
						tableArray.set(i+num, finalList);
					}
					
				}
			}
		}
		return tableArray.get(targetSum);
	}
	public static void main(String[] args) {
	
		
		System.out.println(howSum(7,Arrays.asList(2,3)));
		
		 System.out.println(howSum(7,Arrays.asList(5,3,4,7)));
		 System.out.println(howSum(7,Arrays.asList(2,4)));
		System.out.println(howSum(8,Arrays.asList(2,3,5)));
		 System.out.println(howSum(300,Arrays.asList(7,14)));
		

	}

}
