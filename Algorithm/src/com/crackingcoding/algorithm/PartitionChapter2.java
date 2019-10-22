package com.crackingcoding.algorithm;

import java.util.Scanner;

/* Write code to partition a linked list around a value x,
 *  such that all nodes less than x come before all nodes greater than or equal to x.
If xis contained within the list, the values of x only need to be after the elements less than x (see below). 
The partition element x can appear anywhere in the "right partition"; 
 it does not need to appear between the left and right partitions.
 *  EXAMPLE Input: Output: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5] 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8 Hints  */

public class PartitionChapter2 {	
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
    
    // Mege Sort Partition Logic
    public void partition(int partitionItem) {    	
    	replaceFirstwithPartition(partitionItem);    	
    	Node currentNode =head;
    	Node nextNode =currentNode.next;    	
    	while( currentNode!=null && nextNode!=null ) {    		  		
    		if(nextNode.item <=partitionItem) {
    			swap(currentNode.next,nextNode );
    			currentNode=currentNode.next ; 
    		}
    		nextNode=nextNode.next;     		   		
    	} 
    	swap(head,currentNode);
    }
        
  
    private void replaceFirstwithPartition(int partitionItem) {
    	Node currentNode =head;
    	while( currentNode!=null && currentNode.item!= partitionItem) {
    		currentNode=currentNode.next ;    		
    	}
    	swap(head ,currentNode);		
	}

	private void swap(Node node1, Node node2) {
		int temp = node1.item;
		node1.item = node2.item;
		node2.item =temp;		
	}

	public void readAndProcessData() {
		Scanner scanner = new Scanner(System.in);		
		int count = Integer.parseInt(scanner.next());
		int partitionItem = Integer.parseInt(scanner.next());		
		scanner.nextLine();			
		for(int i =0; i<count ;i++) {
			insert(Integer.parseInt(scanner.next()));			
		}
		System.out.println("Printing the List");
    	print();
		partition(partitionItem);
	
	}
    


	public static void main(String[] args) {
    	
    	PartitionChapter2 c = new PartitionChapter2();
        c.readAndProcessData();    	
    	System.out.println("Printing the List after removing the Node");
    	c.print();
    }
}
