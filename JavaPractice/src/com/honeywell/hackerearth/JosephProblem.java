package com.honeywell.hackerearth;
import java.util.LinkedList;
import java.util.List;
public class JosephProblem {

	/**
	 * @param args
	 */
	public static void main(String[] argv) 
	{
	    List<Integer> r;
	    System.out.println(r = josephus(12, 4, 1));                     // show entire sequence
	    System.out.printf("Person %d is last\n", r.get(r.size() - 1));  // who's last?
	}

	// remove N elements in equal steps starting at specific point
	static List<Integer> josephus(int N, int step, int start)
	{
	    if (N < 1 || step < 1 || start < 1) return null;

	    List<Integer> p = new LinkedList<Integer>();
	    for (int i = 0; i < N; i++)
	        p.add(i+1);

	    List<Integer> r = new LinkedList<Integer>();
	    int i = (start - 2) % N;
	    for (int j = N; j > 0; j--) {
	        i = (i + step) % N--;
	        r.add(p.remove(i--));
	    }

	    return r;
	}

}
