package com.crackingcoding.algorithm;
import java.util.Scanner;
import java.util.Stack;

/* Write a program to sort a stack such that the smallest items are on the top. 
 * You can use an additional temporary stack,
 *  but you may not copy the elements into any other data structure (such as an array).
 *  The stack supports the following operations: push, pop, peek, and is Empty */
public class SortStackChapter3 {
	
	
	public static Stack<Integer> sortStack(Stack<Integer> s ){
		Stack<Integer> r = new Stack<>();
		while(!s.isEmpty()) {
			Integer temp = s.pop();
			while(!r.isEmpty() && temp.intValue() > r.peek().intValue()) {
				s.push(r.pop());
			}
			r.push(temp);
		}		
		return r;		
	}
	
	public void print(Stack<Integer> s) {
		System.out.println(" Printing the stack" );
			s.forEach(k->{
			    System.out.print(k+"->");
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
	
	
	public void processData(int[] values) {
		Stack<Integer> s = new Stack<Integer>();
		for( int i =0; i <values.length; i++) {
			s.push(values[i]);
		}
		System.out.println("Original stack");
		 print(s);
		System.out.println("Sorted stack");
		 print(sortStack(s));
		}
	
	public static void main(String[] args) {
		SortStackChapter3 c1 = new SortStackChapter3();
		int[] values = c1.readData();
		c1.processData(values);
		
		}

}
