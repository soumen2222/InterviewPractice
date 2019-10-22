package com.soumen.generic;

import java.lang.reflect.Method;

public class A {

	/**
	 * @param args
	 */	
	A() {
		A a = new A();
	}
	
	public void b(){
		System.out.println("b called");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (Method m : A.class.getMethods())
		{
			System.out.println(m.toGenericString());
		}
	
	}

}
