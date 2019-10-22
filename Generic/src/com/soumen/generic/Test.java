package com.soumen.generic;

import java.util.List;

class Overloaded2 {
	// compile-time error, cannot overload two methods with same erasure
	public static boolean allZero(List<Integer> ints1,List<Integer> ints2 ) {
		for (int i : ints1)
			if (i != 0)
				return false;
		return true;
	}

	public static double allZero(List<String> strings) {
		for (String s : strings)
			if (s.length() != 0)
				return 0.0;
		return 1.0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
