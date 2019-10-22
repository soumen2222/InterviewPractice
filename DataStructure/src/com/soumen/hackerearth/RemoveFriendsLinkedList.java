package com.soumen.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class SLNode {
	
	private int data;
	private SLNode next;
	
	public SLNode(int data1 , SLNode next1)
	{
		this.data =data1;	
		this.next = next1;
		
	}	
	
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public SLNode getNext() {
		return next;
	}
	public void setNext(SLNode next) {
		this.next = next;
	}
}

class SLinkedList1
{
	private SLNode headNode =null;
	private int length;
	
	
	public SLNode getHeadNode() {
		return headNode;
	}

	public void setHeadNode(SLNode headNode) {
		this.headNode = headNode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void AddAtEnd(int data)
	{
		SLNode currentNode = headNode;
		
		if(currentNode == null) {
			headNode =new SLNode(data,null);
        }
		else
		{
		
		while (currentNode.getNext()!=null) {
			currentNode= currentNode.getNext();
			
		}		
	        currentNode.setNext(new SLNode(data,null));
		}
	  length++;
	}
	
	public SLNode DeleteAtStart()
	{
		
				
		SLNode currentNode = headNode;
		
		if(currentNode!= null) {
			headNode = currentNode.getNext();	
	        currentNode.setNext(null);
	        length--;
		}
	        return currentNode;
		
	}
	
	public void print()
	{
		SLNode currentNode = headNode;
		while (currentNode!=null) {
			System.out.print(currentNode.getData() + " ");
			currentNode= currentNode.getNext();
			
		}
		System.out.println();
	}
	
	public SLNode DeleteAtEnd()
	{
		
		SLNode currentNode = headNode;	
		if(currentNode == null) {
			return null;
	    }
		
		while (currentNode.getNext().getNext()!=null) {				
			currentNode= currentNode.getNext();		
		}
		
		SLNode nextNode = currentNode.getNext();
	    currentNode.setNext(null);
	    length--;
	    return nextNode;
		
	}
	
	
}

public class RemoveFriendsLinkedList {
	
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		   InputStreamReader isr = new InputStreamReader(System.in);
		   BufferedReader br = new BufferedReader(isr);
		   int testCases = Integer.parseInt(br.readLine());
		   
		   for (int i =0; i < testCases ; i++)
		   {
			   RemoveFriendsLinkedList removeFriendsLinkedList=new RemoveFriendsLinkedList();  
			   // need to do for all test cases
			   String friendDeletes = br.readLine();
			   String[] friendDeletessplit = friendDeletes.split(" ");
			   int firends = Integer.parseInt(friendDeletessplit[0]);
	           int toDeleteFriends = Integer.parseInt(friendDeletessplit[1]);
	           
	           
	           String popularity = br.readLine();
			   String[] popularitySplit = popularity.split(" ");
			   
			   SLinkedList1 list = new SLinkedList1();
			   for (String popularityvalue : popularitySplit) {
				   list.AddAtEnd(Integer.parseInt(popularityvalue));				   
				
		     	}
			   removeFriends(firends,toDeleteFriends,list);
			   
			   list.print();
			
		   }
		   
	}

private static void removeFriends(int firends, int toDeleteFriends, SLinkedList1 list) {
			
	SLNode currentNode = list.getHeadNode();
	SLNode previousNode = currentNode;
	while (currentNode.getNext()!=null && toDeleteFriends > 0) {
		
		
		if ((currentNode.getData() < currentNode.getNext().getData()) )
		{
			//remove the Current node.
			
			if(currentNode==list.getHeadNode())
			{
				list.DeleteAtStart();
				currentNode = list.getHeadNode();	
				previousNode = list.getHeadNode();
				
			}
			else
			{
				previousNode.setNext(currentNode.getNext());
				currentNode.setNext(null);
				currentNode = previousNode.getNext();
				
			}
			
			toDeleteFriends--;
		}
		else
		{
			previousNode = currentNode;
			 if(currentNode!=null)
			currentNode= currentNode.getNext();
			
		}
		
		

		
	}

  if (toDeleteFriends>0 )
		{
			// remove form the popularity list from the head element.
	          removeFriends(firends,toDeleteFriends,list);
		}
		
	}

	
	
}
