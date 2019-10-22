package com.crackingcoding.algorithm;

import java.util.Scanner;
import java.util.Stack;

/*Stack Min: How would you design a stack which, in addition to push and pop, 
has a function min which returns the minimum element? Push, pop and min should all operate in 0(1) time.*/
public class MinSumVersion2Chapter3 extends Stack<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4223771070385108085L;
	Stack<Integer> s2;
	
	public MinSumVersion2Chapter3() {
		s2 = new Stack<Integer>();
	}
	
	@Override
	public synchronized Integer pop() {
		   Integer item = super.pop();
			// pop the minimum in the min stack;
			if(item.equals(minValue())) {
			s2.pop();
			}
			return item;
		
		
	}
	@Override
	public Integer push (Integer data) {		
		
		// add the minimum in the min stack;
	 if( data.intValue() <= minValue().intValue()) {
		 s2.push(data);
		}
	 // Add the value in the main stack
	    super.push(data);
	    return data;
		
	}
	public Integer minValue() {
		if(s2.isEmpty()) {
			return Integer.MAX_VALUE;
		}else{
			return s2.peek();
		}
	}
	
	public void print() {
		System.out.println(" Printing Main Stack Data");
		super.forEach(k->{
		    System.out.print("->"+k);
		});
		
		
		System.out.println(" Printing Min Stack Data");
		s2.forEach(k->{
		    System.out.print("->"+k);
		});
		System.out.println();
	}

	public int[] readData() {
		Scanner scanner = new Scanner(System.in);
		int count = Integer.parseInt(scanner.next());
		scanner.nextLine();
		int[] values = new int[count];
		
		for (int i = 0; i < count; i++) {
			values[i] = (Integer.parseInt(scanner.next()));
		}
		return values;
	}
	
	
	public static void main(String[] args) {
		MinSumVersion2Chapter3 c1 = new MinSumVersion2Chapter3();
		int[] values = c1.readData();
		for( int i =0; i <values.length; i++) {
			c1.push(values[i]);
		}
		c1.print();
		System.out.println("Min Stack is : " + c1.minValue());
		c1.pop();
		System.out.println("Min Stack after one pop : " + c1.minValue());
		c1.pop();
		System.out.println("Min Stack after two pop : " + c1.minValue());
		c1.pop();
		System.out.println("Min Stack after three pop : " + c1.minValue());
		c1.pop();
		System.out.println("Min Stack after Four pop : " + c1.minValue());

	}

}
