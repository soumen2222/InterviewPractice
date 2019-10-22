package com.soumen.linkedlist.single;

public class Node {
	
	private int data;
	private Node next;
	
	public Node(int data1 , Node next1)
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
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	
	
	
	

}
