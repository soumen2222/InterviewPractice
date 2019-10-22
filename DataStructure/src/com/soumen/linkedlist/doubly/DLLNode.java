package com.soumen.linkedlist.doubly;

public class DLLNode {
	
	private int data;
	private DLLNode next;
	private DLLNode prev;
	
	public DLLNode(int data)
	{
		this.data =data;	
		prev =null;
		next =null;
	}	
	
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}

	public DLLNode getNext() {
		return next;
	}

	public void setNext(DLLNode next) {
		this.next = next;
	}

	public DLLNode getPrev() {
		return prev;
	}

	public void setPrev(DLLNode prev) {
		this.prev = prev;
	}
	
}
