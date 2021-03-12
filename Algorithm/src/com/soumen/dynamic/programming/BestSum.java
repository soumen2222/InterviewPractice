package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestSum {

	public static List<Integer> bestSum(int targetSum, List<Integer> dataArray ){
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
						if(tableArray.get(i+num)==null || (tableArray.get(i+num)!=null && finalList.size() < tableArray.get(i+num).size() )) {
							tableArray.set(i+num, finalList);
						}	
						
					}
					
				}
			}
		}
		return tableArray.get(targetSum);
	}
	public static void main(String[] args) {
	
		
		System.out.println(bestSum(7,Arrays.asList(2,3)));
		
		 System.out.println(bestSum(7,Arrays.asList(5,3,4,7)));
		 System.out.println(bestSum(7,Arrays.asList(2,4)));
		System.out.println(bestSum(8,Arrays.asList(1,4,5)));
		 System.out.println(bestSum(300,Arrays.asList(7,14)));
		 System.out.println(bestSum(100,Arrays.asList(1,2,5,25)));
		

	}

}
