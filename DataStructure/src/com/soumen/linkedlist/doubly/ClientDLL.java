package com.soumen.linkedlist.doubly;

public class ClientDLL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		DLLLinkedList doublyLinkedList = new DLLLinkedList();
		doublyLinkedList.AddAtStart(5);
		doublyLinkedList.AddAtStart(7);
		doublyLinkedList.AddAtStart(9);
		doublyLinkedList.AddAtStart(1);
		doublyLinkedList.AddAtStart(25);
		doublyLinkedList.AddAtEnd(45);
		doublyLinkedList.AddAtEnd(44);
		doublyLinkedList.AddAtPos(15,9);
		
		
		doublyLinkedList.print();
		System.out.println("linked list size is: " + doublyLinkedList.getLength());
		
		/*
		Node data= singleLinkedList.DeleteAtStart();
		System.out.println("Deleted data is : " + data.getData());
				
		singleLinkedList.print();
		System.out.println("linked list size is: " + singleLinkedList.getLength());
		
		Node data1= singleLinkedList.DeleteAtEnd();
		System.out.println("Deleted data is : " + data1.getData());		
		singleLinkedList.print();
		
		System.out.println("linked list size is: " + singleLinkedList.getLength());
		
		
		Node data2= singleLinkedList.DeleteAtPos(1);
		if (data2!=null){
			System.out.println("Deleted data is : " + data2.getData());	
		}
		else{
			System.out.println("Data not available to delete");	
		}
		
        singleLinkedList.print();
		
		System.out.println("linked list size is: " + singleLinkedList.getLength());
		
		*/
	}

}
