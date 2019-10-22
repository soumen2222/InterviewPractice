package com.soumen.coding.challenge;

import java.util.Scanner;
import java.util.Stack;

class MyQueue<T>
{
	
	private Stack<T> stack1 = new Stack<>();
	private Stack<T> stack2 = new Stack<>();
	

	public void enqueue(T nextInt) {
		
		stack1.push(nextInt);
		
	}

	public void dequeue() {
		
		shift();
		
		stack2.pop();
		
	}

	public T peek() {

		shift();
	
		return stack2.peek();
		
	}

	private void shift() {
		if(stack2.empty() && !stack1.empty() )
		{
			while(!stack1.empty())
			{
				stack2.push(stack1.pop());
			}
		}
	}

	
}



public class QueuesTale {
    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<Integer>();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
              queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
              queue.dequeue();
            } else if (operation == 3) { // print/peek
              System.out.println(queue.peek());
            }
        }
        scan.close();
    }
}
