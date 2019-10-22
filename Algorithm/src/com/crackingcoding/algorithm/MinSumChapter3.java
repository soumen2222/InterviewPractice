package com.crackingcoding.algorithm;

import java.util.EmptyStackException;
import java.util.Scanner;

/*Stack Min: How would you design a stack which, in addition to push and pop, 
has a function min which returns the minimum element? Push, pop and min should all operate in 0(1) time.*/
public class MinSumChapter3 {
	
	private static class StackNode{
		
		private int data;
		private StackNode next ;
		
		public StackNode(int data){
			this.data =data;
		}
	}
	
	private StackNode top;
	private StackNode min;
	
	public int pop() {
		if(top!=null && min!=null) {
			int item = top.data;
			top = top.next;
			// pop the minimum in the min stack;
			if(item==minValue()) {
			min =min.next;
			}
			return item;
		}else {
			throw new EmptyStackException();
		}	
		
	}
	
	public void push (int data) {		
		
		// add the minimum in the min stack;
	 if( data <= minValue()) {
		StackNode minData = new StackNode(data);
		minData.next =min;
		min = minData;}
	 // Add the value in the main stack
	 StackNode newData = new StackNode(data);		
		newData.next =top;
		top = newData;
		
	}
	public int minValue() {
		if(min==null) {
			return Integer.MAX_VALUE;
		}else{
			return minPeek();
		}
	}
	public int peek() {
		if(top==null) throw new EmptyStackException();
		return top.data;
	}
	public int minPeek() {
		if(min==null) throw new EmptyStackException();
		return min.data;
	}
		
	public void print() {
		System.out.println(" Printing Stack Data");
		StackNode Currenttop = top;
		while(Currenttop!=null) {
			System.out.print(Currenttop.data + " ");
			Currenttop = Currenttop.next;
		}
		
		System.out.println(" Printing Min Stack Data");
		StackNode Currentmin = min;
		while(Currentmin!=null) {
			System.out.print(Currentmin.data + "--> ");
			Currentmin = Currentmin.next;
		}
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
		MinSumChapter3 c1 = new MinSumChapter3();
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
