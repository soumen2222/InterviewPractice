package com.soumen.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OverloadingTest {

	/**
	 * @param args
	 */
	//1 -- check one item
	public static <T> void containsAtLeast(String message,
	                                       T expectedItem,
	                                       Collection<? extends T> found) {
	    if (!found.contains(expectedItem))
	    {
	    	System.out.println("Assert fails for "+ expectedItem);
	    }
	            
	}

	//2 -- check several items
	public static <T> void containsAtLeast(String message,
	                                       Collection<? extends T> expectedItems,
	                                       Collection<? extends T> found) {
	    for (T exptetedItem : expectedItems)
	        containsAtLeast(message, exptetedItem, found);        
	}

	//3 -- check several items, without message parameter
	public static <T> void containsAtLeast(Collection<? extends T> expectedItems,
	                                       Collection<? extends T> found) {
	    containsAtLeast(null, expectedItems, found);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Integer> myInts1 = Arrays.asList(1,2,3,4,7);
		List<Integer> myInts2 = Arrays.asList(3,4);
		containsAtLeast(myInts1, myInts2);
		
       
		List<String> l = Arrays.asList("zero","one");
		String[] a = l.toArray(new String[0]);
		
		
		//Primitive types
		List<Integer> l1 = Arrays.asList(0,1,2);
		
		int[] a1 = new int[l1.size()];
		for (int i=0; i<l.size(); i++) 
			a1[i] = l1.get(i);
		

	}

}
