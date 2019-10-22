package com.crackingcoding.algorithm;

import java.util.Scanner;

/*Write code to remove duplicates from an unsorted linked list. FOLLOW UP How would you solve
this problem if a temporary buffer is not allowed? */

public class RemoveDupsChapter2<Item> {	
	private int n;          // size of the Linked List
    private Node head;     // Head of the linked list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        
        Node(Item item){
        	this.item =item;
        	this.next =null;
        }
    }
    
    // Insert into head of Linked List
    
    public void insert(Item item) {    	
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
    
    public void remove(Item item) {
    	Node currentNode =head;
    	Node prevNode =head;
    	while(currentNode!=null && (currentNode.item !=item)) {    		 
    		prevNode = currentNode;
    		currentNode = currentNode.next;
    	}
    	if(prevNode==head) {
    		//Delete head node;
    		prevNode.next=currentNode.next;
    		head = prevNode.next;
    		currentNode =null;
    		n--;
    	}else {
    		prevNode.next=currentNode.next;
    		currentNode =null;
    		n--;
    	}
    }
    
    // Remove Duplicates    
    public void removeDips() {    	
       	for(Node currentNode =head; currentNode!=null; currentNode=currentNode.next) {
    		for(Node runner =currentNode.next; runner!=null; runner=runner.next) {
    			if(runner.item==currentNode.item) {
    				//Remove the Item
    				remove(runner.item);
    				currentNode =head;
    			}
    		}
    	}
    	
    }
    public int[] readData() {
		Scanner scanner = new Scanner(System.in);		
		int count = Integer.parseInt(scanner.next());
		int[] itemList = new int[count];
		scanner.nextLine();			
		for(int i =0; i<count ;i++) {
			itemList[i]= Integer.parseInt(scanner.next());			
		}
		return itemList;
	
	}
    
    @SuppressWarnings("boxing")
	public static void main(String[] args) {
    	
    	RemoveDupsChapter2<Integer> c = new RemoveDupsChapter2<>();
    	int[] itemLists = c.readData();
    	for(int i =0; i <itemLists.length;i++) {
    		c.insert(itemLists[i]);
    	}
    	System.out.println("Printing the List");
    	c.print();
    	c.removeDips();
    	System.out.println("Printing the List after removing dups");
    	c.print();
    }
}
