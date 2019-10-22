package com.honeywell.java.reflect;

import java.lang.reflect.Constructor;

public class ReflectConsexp {

	/**
	 * @param args
	 */
	// TODO Auto-generated method stub
		public static void main(final String[] args) throws Exception {
	        Constructor<ReflectCons> constructor = ReflectCons.class.getDeclaredConstructor(new Class[0]);
	        constructor.setAccessible(true);
	        ReflectCons foo = constructor.newInstance(new Object[0]);
	        System.out.println(foo);
	    }
}
