package com.soumen.generic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cell2<T> {
	private final int id;
	private final T value;
	private static int count = 0;
	private static List<Object> values = new ArrayList<Object>(); // illegal
    	
	public Cell2(T value) {
		this.value = value;
		values.add(value);
		id=nextId();
	}

	public T getValue() {
		return value;
	}

	public static List<Object> getValues() {
		return values;
	} // illegal
	
	private static synchronized int nextId() { return count++; }
	public int getId() { return id; }
	public static synchronized int getCount() { return count; }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Cell2<String> a = new Cell2<String>("one");
		Cell2<Integer> b = new Cell2<Integer>(2);
		assert Cell2.getValues().toString().equals("[one, 2]");
		
		System.out.println("count  "+ getCount());
		System.out.println("b id is  " + b.getId());
		
		

	}

}
