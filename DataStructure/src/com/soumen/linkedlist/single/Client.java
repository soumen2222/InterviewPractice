package com.soumen.linkedlist.single;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		SingleLinkedList singleLinkedList = new SingleLinkedList();
		singleLinkedList.AddAtStart(5);
		singleLinkedList.AddAtStart(7);
		singleLinkedList.AddAtStart(9);
		singleLinkedList.AddAtStart(1);
		singleLinkedList.AddAtStart(25);
		singleLinkedList.AddAtEnd(60);		
		singleLinkedList.AddAtPos(77,5);
		
		singleLinkedList.print();
		singleLinkedList.reverse();
		singleLinkedList.print();
		
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
		
		
	}

}
