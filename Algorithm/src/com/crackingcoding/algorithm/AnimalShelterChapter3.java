package com.crackingcoding.algorithm;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/*An animal shelter, which holds only dogs and cats, operates on a strictly"first in,
 *  first out" basis. People must adopt either the "oldest" (based on arrival time) of all animals 
 *  at the shelter, or they can select whether they would prefer a dog or a cat (and will receive the 
 *  oldest animal of that type). They cannot select which specific animal they would like. Create the 
 *  data structures to maintain this system and implement operations such as enqueue, dequeueAny, 
 * dequeueDog, and dequeueCat. You may use the built-in Linked list data structure */
public class AnimalShelterChapter3 {
	
	LinkedList<Dog> dogs = new LinkedList<>();
	LinkedList<Cat> cats = new LinkedList<>();
	private int order =0;
	
	public void enqueue(Animal a) {	
		a.setOrder(order);
		order++;
		
		if(a instanceof Dog) {
			dogs.addLast((Dog) a);			
		}
		if(a instanceof Cat) {
			cats.addLast((Cat) a);			
		}
		
	}
	
	public Dog dequeDog() {
		return dogs.removeFirst();
	}
	
	public Cat dequeCat() {
		return cats.removeFirst();
	}	
	
	public Animal dequeAny()
	{
		if(dogs.size()==0) return cats.pop();
		if(cats.size()==0) return dogs.pop();
		if(dogs.peek().getOrder() < cats.peek().getOrder()) {
		return dogs.pop();
		}
		else {
			return cats.pop();
		}
		
	}

	public int[] readData() {
		Scanner scanner = new Scanner(System.in);
		int count = Integer.parseInt(scanner.next());
		scanner.nextLine();
		int[] values = new int[count];
		
		for (int i = 0; i < count; i++) {
			values[i] = (Integer.parseInt(scanner.next()));
		}
		return values;
	}
	
	
	public void processData(int[] values) {
		Stack<Integer> s = new Stack<Integer>();
		for( int i =0; i <values.length; i++) {
			s.push(values[i]);
		}
		
		}
	
	public static void main(String[] args) {
		AnimalShelterChapter3 c1 = new AnimalShelterChapter3();
		int[] values = c1.readData();
		c1.processData(values);
		
		}

}
