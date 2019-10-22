package com.soumen.linkedlist.single;

public class SingleLinkedList {
	
	private Node headNode =null;
	private int length;
	
	public SingleLinkedList()
	{
		length =0;
	}
	
		
	public Node getHeadNode() {
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
			headNode= new Node(data,null);
			
		}
		
		else{
			
			Node newNode = new Node(data,headNode);
			headNode=newNode;
			
		}
		length++;
	}
	
	// Add at the End
	
	public void AddAtEnd(int data)
	{
		Node currentNode = headNode;
		
		if(currentNode == null) {
			headNode.setNext(new Node(data,null));
        }
		
		while (currentNode.getNext()!=null) {
			currentNode= currentNode.getNext();
			
		}		
	  currentNode.setNext(new Node(data,null));
	  length++;
	}
	
	// Add at the Middle
	
	public void AddAtPos(int data, int pos)
	{  
		int position = 1;
		Node currentNode = headNode;
		
		if(currentNode == null) {
			headNode.setNext(new Node(data,null));
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
	  currentNode.setNext(new Node(data,currentNode.getNext()));
	  length++;
	}
	
	
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

public void reverse()
{
	Node currentNode = headNode;
	
	while(currentNode!=null){
	if (currentNode==headNode){
		currentNode = currentNode.getNext();
		headNode.setNext(null);
	    }
	
	Node previousNode = currentNode;
	currentNode = currentNode.getNext();
	previousNode.setNext(headNode);
	headNode=previousNode;
	}
	
	
}
	
// Find Operation
	
	
	
	
	
	public void print()
	{
		Node currentNode = headNode;
		while (currentNode!=null) {
			System.out.print(currentNode.getData() + " --> ");
			currentNode= currentNode.getNext();
			
		}
		System.out.println();
	}

}
