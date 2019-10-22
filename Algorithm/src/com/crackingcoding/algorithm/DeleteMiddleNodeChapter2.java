package com.crackingcoding.algorithm;

import java.util.Scanner;

/* Implement an algorithm to delete a node in the middle
 *  (i.e., any node but the first and last node, not necessarily the exact middle) 
 *  of a singly linked list, given only access to that node. 
 *  EXAMPLE lnput:the node c from the linked lista->b->c->d->e->f Result: 
 * nothing is returned, but the new linked list looks like a->b->d->e->f  */

public class DeleteMiddleNodeChapter2 {	
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
    
    // Print Linked List
    
    public void print() {
    	Node startNode =head;
    	while(startNode!=null) {
    		System.out.print(startNode.item + " ");    		
    		startNode = startNode.next;
    	}
    	System.out.println();
    }
    
    // Remove from linked List
    
    public void removeNode(Node node) {
    	// Copy the Node data
    	Node nextNode = node.next;
    	node.item = nextNode.item;
    	
    	// Delete the next node;
    	node.next=nextNode.next;
    	nextNode =null;
    	n--;    	
    }
    
  
    public void readAndProcessData() {
		Scanner scanner = new Scanner(System.in);		
		int count = Integer.parseInt(scanner.next());
		int nodeItem = Integer.parseInt(scanner.next());		
		scanner.nextLine();			
		for(int i =0; i<count ;i++) {
			insert(Integer.parseInt(scanner.next()));			
		}
		System.out.println("Printing the List");
    	print();
		removeNode(getNode(nodeItem));
	
	}
    
    private Node getNode(int nodeDetail) {		
    	Node currentNode =head;    	
    	while(currentNode!=null && (currentNode.item !=nodeDetail)) { 
       		currentNode = currentNode.next;
    	}
		return currentNode;
	}


	public static void main(String[] args) {
    	
    	DeleteMiddleNodeChapter2 c = new DeleteMiddleNodeChapter2();
        c.readAndProcessData();    	
    	System.out.println("Printing the List after removing the Node");
    	c.print();
    }
}
