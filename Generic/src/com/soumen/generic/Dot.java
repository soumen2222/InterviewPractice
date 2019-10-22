package com.soumen.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Number> nums = new ArrayList<Number>();
		nums.add(2);
		nums.add(3.14);
		assert nums.toString().equals("[2, 3.14]");
		
		
		
		List<Double> u = new ArrayList<Double>();
		u.add(21.0);
		List<Double> v = new ArrayList<Double>();
		v.add(22.0);
		System.out.println(dot(u,v));
	}
	
	public static double dot(List<Double> u, List<Double> v) {
		if (u.size() != v.size())
		throw new IllegalArgumentException("different sizes");
		double d = 0;
		Iterator<Double> uIt = u.iterator();
		Iterator<Double> vIt = v.iterator();
		while (uIt.hasNext()) {
		assert uIt.hasNext() && vIt.hasNext();
		d += uIt.next() * vIt.next();
		}
		assert !uIt.hasNext() && !vIt.hasNext();
		return d;
		}

}
