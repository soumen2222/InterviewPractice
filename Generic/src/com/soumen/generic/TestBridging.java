package com.soumen.generic;

import java.lang.reflect.Method;

public class TestBridging {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point p = new Point(1,2);
		Point q = p.clone();
		
		for (Method m : Point.class.getMethods())
			if (m.getName().equals("clone"))
			System.out.println(m.toGenericString());
	}

}
