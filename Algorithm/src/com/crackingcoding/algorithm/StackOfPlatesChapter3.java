package com.crackingcoding.algorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/*Imagine a (literal) stack of plates. If the stack gets too high, it might topple.
 *  Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. 
 *  Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be composed of several stacks 
 *  and should create a new stack once the previous one exceeds capacity. SetOfStacks. push() and SetOfStacks. pop() 
 *  should behave identically to a single stack (that is, pop () 
 * should return the same values as it would if there were just a single stack). */
public class StackOfPlatesChapter3 {
		
	private List<Stack<Integer>> listofStacks = new ArrayList<>();
	private static int maxStackSize = 2;
	
	public Integer pop() {
		Integer popedItem =-1;
		// Step1 Find the last index of the List 
		if(!listofStacks.isEmpty()) {
		int lastIndex = listofStacks.size() -1;
		// Step 2 : Get the Stack available in last index
		
		Stack<Integer> s = listofStacks.get(lastIndex);	
		// Step3 : pop from the stack
		
		popedItem = s.pop();
		// Step 4: If the stack is empty, remove the index from the list.
		  		
		if( s.isEmpty()) {
			listofStacks.remove(lastIndex);
		}
		}
		return popedItem;
	}
	

	public Integer popAt(int index) {
		Integer popedItem =-1;
		if(index > (listofStacks.size()-1) ) return popedItem;
		// Step1 Find the last index of the List 
		if(!listofStacks.isEmpty()) {
		
		Stack<Integer> s = listofStacks.get(index);;
		// Step3 : pop from the stack
		
		popedItem = s.pop();
		// Step 4: If the stack is empty, remove the index from the list.
		  		
		if( s.isEmpty()) {
			listofStacks.remove(index);
		}
		}
		return popedItem;
	}
	
	public Integer push (Integer data) {		
		// Step1 Find the last index of the List 
		     if(!listofStacks.isEmpty()) {
				int lastIndex = listofStacks.size() -1;
				// Step 2 : Get the Stack available in last index
				
				Stack<Integer> s = listofStacks.get(lastIndex);
				// Step3 : Check the size , If less than maxSize then add , otherwise create a new satck and add to next index
				
				if(s.size()< maxStackSize) {
					s.add(data);
				}else {
					Stack<Integer> newStack = new Stack<>();
					newStack.add(data);
					listofStacks.add(newStack );
				}	
		     }else {
		    	    Stack<Integer> newStack = new Stack<>();
					newStack.add(data);
					listofStacks.add(newStack );
		     }
				return data;
	}
	
	public void print() {
		int index=0;
		for (Stack<Integer> stack : listofStacks) {
			System.out.println(" Printing Main Stack Data" + index++);
			stack.forEach(k->{
			    System.out.print("->"+k);
			});
			System.out.println();
		}		
		System.out.println("StackList Size:  " + listofStacks.size());
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
		StackOfPlatesChapter3 c1 = new StackOfPlatesChapter3();
		int[] values = c1.readData();
		for( int i =0; i <values.length; i++) {
			c1.push(values[i]);
		}
		c1.print();		
		c1.pop();
		c1.print();	
		c1.pop();		
		c1.print();	
		c1.pop();
		
		}

}
