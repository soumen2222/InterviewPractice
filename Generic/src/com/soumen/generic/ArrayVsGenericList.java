package com.soumen.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ArrayVsGenericList {

	/**
	 * @param args
	 */
	
	//array subtyping is covariant, meaning that type S[] is considered to be a subtypeof T[] whenever S is a subtype of T.
	public ArrayVsGenericList()
	{
		
	}
	
	public static void Covarient()
	{
		
		//Arrays are co-varient
		Integer[] ints = new Integer[] {1,2,3};
		Number[] nums = ints; //Integer[] is considered a subtype of Number[], Substitution Principle
	//	nums[2] = 3.14; // an array store exception is raised if the reified type is not compatible with the assigned value

	}
	
	public static void Invarient()
	{
		
		//generics is invariant, meaning that type List<S> is not considered to be a subtype of List<T>,
		List<Integer> ints = Arrays.asList(1,2,3);
	//	List<Number> nums = ints; //List<Integer> is not considered to be a subtype of List<Number>,
	//	nums[2] = 3.14; // an array store exception is raised if the reified type is not compatible with the assigned value

	}
	
	
	public static void CovarientGenerics()
	{
		
		//Covariant in generics with wild card: type List<S> is considered to be a subtype of List<? extends T> when S is a subtype of T
		List<Integer> ints = Arrays.asList(1,2,3);
		List<? extends Number> nums = ints;
//		nums.set(2, 3.14); // the problem is detected at compile time, not run time in comparision to array.

	}
	
	public static void ContravarientGenerics()
	{
		
		//contravariant subtyping for generics, in that type List<S> is considered to be a subtype of List<? super T> when S is a supertype of T (as opposed to a subtype)
		List<Object> objects = Arrays.<Object>asList(1,"two");
		List<? super Number> nums = objects;
		nums.set(2, 3.14); 

	}
	
	public static <T> void reverse(List<T> list) {
		List<T> tmp = new ArrayList<T>(list);
		for (int i = 0; i < list.size(); i++) {
		list.set(i, tmp.get(list.size()-i-1));
		}
		}
	
	public static <T extends Comparable<T>> T max(Collection<T> coll) {
		T candidate = coll.iterator().next();
		
		for (T elt : coll) {
		if (candidate.compareTo(elt) < 0) candidate = elt;
		}
		return candidate;
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object obj = "one";
		List<Object> objs = Arrays.<Object>asList("one", 2, 3.14, 4);
		List<Integer> ints = Arrays.asList(2, 4);
		assert objs.contains(obj) : "Error!!!";
		assert objs.containsAll(ints);
		assert !ints.contains(obj);
		assert !ints.containsAll(objs);
		reverse(objs);
		for (Object ob : objs) {
			System.out.println(ob);
		}
		
		
		List<Number> nums = new ArrayList<Number>();
		List<? super Number> sink = nums;
		List<? extends Number> source = nums;
		for (int i=0; i<10; i++) sink.add(i);
		double sum=0; 
		for (Number num : source) sum+=num.doubleValue();		
		System.out.println(sum);
		
		
		List<Integer> ints1 = Arrays.asList(3,5,6);
		assert max(ints1) == 6;
		
	}

	
	
}
