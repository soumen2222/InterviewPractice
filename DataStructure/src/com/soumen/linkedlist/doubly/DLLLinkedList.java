package com.soumen.linkedlist.doubly;

public class DLLLinkedList {
	
	private DLLNode headNode =null;
	private int length;
	
	public DLLLinkedList()
	{
		length =0;
	}
	
		
	public DLLNode getHeadNode() {
		return headNode;
	}


	public int getLength() {
		return length;
	}


//Adding new element at the beginning of the linked list
	
	public synchronized void AddAtStart(int data)
	{
		
		if (headNode==null)
		{
			headNode= new DLLNode(data);
			
		}
		
		else{
			
			DLLNode newNode = new DLLNode(data);
			newNode.setNext(headNode);
			headNode.setPrev(newNode);
			headNode=newNode;
		}
		length++;
	}
	
	// Add at the End
	
	public void AddAtEnd(int data)
	{
		DLLNode currentNode = headNode;
		
		if(currentNode == null) {
			headNode = new DLLNode(data);
			     }
		
		while (currentNode.getNext()!=null) {
			currentNode= currentNode.getNext();
			
		}		
	
		DLLNode newNode = new DLLNode(data);
		
	  currentNode.setNext(newNode);
	  newNode.setPrev(currentNode);
	  
	  length++;
	}
		
	
		
	// Add at the Middle
	
	public void AddAtPos(int data, int pos)
	{  
		int position = 1;
		DLLNode currentNode = headNode;
		
		if(pos>length){
			pos=length;
		}
		
		if(currentNode == null) {
			headNode = new DLLNode(data);
        }
		
		while ( position!=pos) {
			if (currentNode.getNext()!=null){
			currentNode= currentNode.getNext();
			position++;
			}
			else
			{
				break;
			}
		}		
		
		DLLNode newNode = new DLLNode(data);
		if(currentNode.getNext()!=null)
		{
		newNode.setNext(currentNode.getNext());
		newNode.setPrev(currentNode);
		currentNode.getNext().setPrev(newNode);
		currentNode.setNext(newNode);
		
		length++;
		}
		
		else{
			AddAtEnd(data);
		}
	    
	}
	
	/*
	
/// Delete Operation
	
		
	
	//Delete headnode
	
public Node DeleteAtStart()
{
	
	Node currentNode = headNode;
	
	if(currentNode!= null) {
		headNode = currentNode.getNext();	
        currentNode.setNext(null);
        length--;
	}
        return currentNode;
	
}


//Delete End Node


public Node DeleteAtEnd()
{
	
	Node currentNode = headNode;	
	if(currentNode == null) {
		return null;
    }
	
	while (currentNode.getNext().getNext()!=null) {				
		currentNode= currentNode.getNext();		
	}
	
	Node nextNode = currentNode.getNext();
    currentNode.setNext(null);
    length--;
    return nextNode;
	
}


//Delete Middle Node


public Node DeleteAtPos(int pos)
{
	
	int position = 1;
	Node currentNode = headNode;
	Node previousNode =null;
		
	if (pos>length)
	{
		return null;
	}
	
	if(currentNode == null) {
		return null;
    }
	
	while ( position!=pos) {
		if (currentNode.getNext()!=null){
		previousNode = currentNode;
		currentNode= currentNode.getNext();
		position++;
		}
		else
		{
			break;
		}
	}		

	if (previousNode!=null)
	{
		previousNode.setNext(currentNode.getNext());
		currentNode.setNext(null);	
	}
	else
	{
		 headNode = currentNode.getNext();	
		 currentNode.setNext(null);
	}
	
	
    length--;
    return currentNode;
    
   
	
}


	
// Find Operation
	
	
	*/
	
	
	public void print()
	{
		DLLNode currentNode = headNode;
		while (currentNode!=null) {
			System.out.print(currentNode.getData() + " --> ");
			currentNode= currentNode.getNext();
			
		}
		System.out.println();
	}

}
