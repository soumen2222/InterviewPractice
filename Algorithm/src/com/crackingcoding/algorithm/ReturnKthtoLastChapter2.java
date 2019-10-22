package com.crackingcoding.algorithm;

import java.util.Scanner;

/*Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list. */

public class ReturnKthtoLastChapter2 {

	private int n; // size of the Linked List
	private Node head; // Head of the linked list

	// helper linked list class
	private class Node {
		private int item;
		private Node next;

		Node(int item) {
			this.item = item;
			this.next = null;
		}
	}

	public void insert(int item) {
		Node newNode = new Node(item);
		if (head != null) {
			newNode.next = head;
			head = newNode;
		} else {
			head = newNode;
		}
		n++;
	}

	public void print() {
		Node startNode = head;
		while (startNode != null) {
			System.out.print(startNode.item + " ");
			startNode = startNode.next;
		}
		System.out.println();
	}

	public void readData() {
		Scanner scanner = new Scanner(System.in);
		int count = Integer.parseInt(scanner.next());
		int kthelement = Integer.parseInt(scanner.next());
		scanner.nextLine();
		for (int i = 0; i < count; i++) {
			insert(Integer.parseInt(scanner.next()));
		}
		System.out.println("Printing the List");
		print();
		findKthtolastElement(kthelement);

	}

	private void findKthtolastElement(int kthelement) {
		Node currentNode = head;
		int count =0;
		while (currentNode != null && count <kthelement ) {
			currentNode = currentNode.next;
			count++;
		}
		System.out.println("Printing Kth to Last element");
		while (currentNode != null ) {			
			System.out.print(currentNode.item + " ");
			currentNode = currentNode.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		ReturnKthtoLastChapter2 c = new ReturnKthtoLastChapter2();
		c.readData();		
	}

}
