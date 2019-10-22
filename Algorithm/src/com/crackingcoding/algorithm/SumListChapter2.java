package com.crackingcoding.algorithm;

import java.util.Scanner;

/* You have two numbers represented by a linked list, where each node contains a single digit. 
 * The digits are stored in reverse order, such that the 1 's digit is at the head of the list.
 *  Write a function that adds the two numbers and returns the sum as a linked list.   */

public class SumListChapter2 {	
	private int n;          // size of the Linked List
    private Node head;     // Head of the linked list

    // helper linked list class
    private class Node {
        private int item;
        private Node next;
        
        Node(int item){
        	this.item =item;
        	this.next =null;
        }
    }
    
    // Insert into head of Linked List
    
    public void insert(int item) {    	
    	Node newNode = new Node(item);       	
    	if ( head!=null) {
    		newNode.next =head;
    		head = newNode;    		
    	}else {
    		head =newNode;
    	}    
    	n++;
    }
    
    public int insertAfter(int item) {    	
    	
    	Node currentNode = head;
    	if(currentNode==null) {
    		insert(item);
    		return item;
    	}
    	while( currentNode.next!=null) {
    		currentNode = currentNode.next;    		   		
    	}
    	Node newNode = new Node(item);
        currentNode.next =newNode;    	    
    	n++;
    	return item;
    }
    
    // Print Linked List
    
    public void print() {
    	Node startNode =head;
    	while(startNode!=null) {
    		System.out.print(startNode.item + " ");    		
    		startNode = startNode.next;
    	}
    	System.out.println();
    }
    
   
	public void readAndProcessData() {
		Scanner scanner = new Scanner(System.in);		
		int count = Integer.parseInt(scanner.next());		
		scanner.nextLine();	
		SumListChapter2 c1 = new SumListChapter2();
		for(int i =0; i<count ;i++) {
			c1.insert(Integer.parseInt(scanner.next()));			
		}
		scanner.nextLine();	
		SumListChapter2 c2 = new SumListChapter2();
		for(int i =0; i<count ;i++) {
			c2.insert(Integer.parseInt(scanner.next()));			
		}
		System.out.println("Printing the First List");
    	c1.print();
    	System.out.println("Printing the Second List");
    	c2.print();
    	
		sum(c1,c2);
		
	
	}

	private void sum(SumListChapter2 c1, SumListChapter2 c2) {
		Node currentNodec1= c1.head;
		Node currentNodec2= c2.head;	
		int divisor =0;
		SumListChapter2 sumNode = new SumListChapter2();
		while(currentNodec1!=null && currentNodec2!=null) {
			
			int sum = currentNodec1.item + currentNodec2.item +divisor;
			int rem = (sum) %10;
			sumNode.insertAfter(rem);
			divisor = (sum)/10;
			currentNodec1= currentNodec1.next;
			currentNodec2= currentNodec2.next;
			
		}
		System.out.println("Printing the List after adding the Node");
		sumNode.print();
	}

	public static void main(String[] args) {
    	
    	SumListChapter2 c = new SumListChapter2();
        c.readAndProcessData();    	
    	
    	
    }
}
