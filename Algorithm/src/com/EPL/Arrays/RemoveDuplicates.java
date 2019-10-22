package com.EPL.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveDuplicates {

	// Sample input - (2,3,5,5,7,11,11,11,13)
	// Output = (2,3,5,7,11,13,0,0,0)
	
	public static List<Integer> removeDups (List<Integer> A) {
		
		int emptyIndex =0;
	
		for (int i = 0; i < A.size()-1; i++) 
		{
			if(A.get(i)==A.get(i+1))
			{
				if(emptyIndex ==0)
				emptyIndex =i+1;
			}
			else
			{  if(emptyIndex>0){
				A.set(emptyIndex, A.get(i+1));
				emptyIndex++;
				}
			}
		}
		
		for (int i = emptyIndex; i < A.size() && emptyIndex>0 ; i++)
		{
			A.set(i, 0);
		}
		
		
		return A ;
		}

	public static int deleteDuplicates(List<Integer> A) {
		if (A.isEmpty()) {
		return 0;
		}
		int emptylndex = 1;
		for (int i = 1; i < A.size(); ++i) {
		if (!A.get(emptylndex - 1).equals(A.get (i))) {
		A.set(emptylndex++, A.get(i));
		}
		}
		return emptylndex;
	}
	
	
	public static int allow2Duplicates(List<Integer> A) {
		if (A.isEmpty()) {
		return 0;
		}
		int emptylndex = 1;
		int dups=0;
		for (int i = 1; i < A.size(); ++i) {
		if (!A.get(emptylndex - 1).equals(A.get (i))) {
		A.set(emptylndex++, A.get(i));
		dups =0;
		}else
		{
			if (A.get(emptylndex - 1).equals(A.get (i)) && dups++ <1) {
				A.set(emptylndex++, A.get(i));
				}
		}
		
		
		
		}
		return emptylndex;
	}
	public static void main(String[] args) {
				
		List<Integer> val = new ArrayList<>(Arrays.asList(2,3,5,5,5,7,11,11,11,11,12,13,14,15,156));
		System.out.print(allow2Duplicates(val) + " " );
		/*List<Integer> output = removeDups(val);
		for (Integer integer : output) {
			System.out.print(integer+ " ");
		}*/
		
		

	}

	
	
}
