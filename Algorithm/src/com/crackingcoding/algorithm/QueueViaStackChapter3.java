package com.crackingcoding.algorithm;
import java.util.Scanner;
import java.util.Stack;

/*Implement a MyQueue class which implements a queue using two stacks */
public class QueueViaStackChapter3 {
		
	private Stack<Integer>  stackNewest , stackOldest;	
	
	public QueueViaStackChapter3() {
		stackNewest = new Stack<>();
		stackOldest = new Stack<>();
	}
	
	public int size() {
		return stackNewest.size() + stackOldest.size();
	}
	
	
	public void add (Integer data) {		
		stackNewest.push(data);
	}
	
	public void shiftStacks() {
		if(stackOldest.isEmpty()) {
			while(!stackNewest.isEmpty()) {
				stackOldest.push(stackNewest.pop());
			}
		}
	}
	
	public void remove() {
		shiftStacks();
		stackOldest.pop();
	}

	public Integer peek() {
		shiftStacks();
		return stackOldest.peek();
	}
	
	public void print() {
			
			System.out.println(" Printing Oldest Stack Data" );
			stackOldest.forEach(k->{
			    System.out.print("->"+k);
			});
			System.out.println();
			
			System.out.println(" Printing Oldest Stack Data" );
			stackNewest.forEach(k->{
			    System.out.print("->"+k);
			});
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
		QueueViaStackChapter3 c1 = new QueueViaStackChapter3();
		int[] values = c1.readData();
		for( int i =0; i <values.length; i++) {
			c1.add(values[i]);
		}
		c1.print();		
		c1.remove();
		c1.print();	
		c1.remove();		
		c1.print();	
		c1.remove();
		
		}

}
