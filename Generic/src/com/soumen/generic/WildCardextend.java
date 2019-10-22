package com.soumen.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildCardextend {

	/**
	 * @param args
	 */
	public static <T> void copy(List<? extends T> src,List<? super T> dst) {
				
		for(T number : src) {
	        dst.add(number);
	    }
		}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Object> dst = new ArrayList<Object>();
		dst.add(4);
		List<Number> nums = new ArrayList<Number>();
		List<Integer> ints = Arrays.asList(1, 2);
		List<Double> dbls = Arrays.asList(2.78, 3.14);
		nums.addAll(ints);  //addAll(Collection<? extends E> c);
		//nums.addAll(dbls);
		
		List<Integer> myInts = Arrays.asList(1,2,3,4,7);
		List<Double> myDoubles = Arrays.asList(3.14, 6.28);
		List<Object> myObjs = new ArrayList<Object>();

		copy(myInts, myObjs);
		copy(myDoubles, myObjs);
		
		for (Object num : myObjs) {
			System.out.println("dst" + num);
		}
		
		System.out.println("+++++++++++");
		copy(ints,dst);
		
		for (Object num1 : dst) {
			System.out.println("dst" + num1);
		}
		
		
		
		
	}

}
