package com.soumen.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class TestComparator {

	/**
	 * @param args
	 */

	static Comparator<String> sizeOrder = new Comparator<String>() {
		public int compare(String s1, String s2) {
			return s1.length() < s2.length() ? -1
					: s1.length() > s2.length() ? 1 : s1.compareTo(s2);
		}
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Collection<String> strings = Arrays.asList("from","aaa","to","zzz");
		assert Collections.max(strings).equals("zzz");
		assert Collections.min(strings).equals("aaa");
		assert Collections.max(strings,sizeOrder).equals("from");
		assert Collections.min(strings,sizeOrder).equals("to");
	}

}
