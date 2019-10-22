package com.soumen.generic.collectionexp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestCol {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		PhoneTask mikePhone = new PhoneTask("Mike", "987 6543");
		PhoneTask paulPhone = new PhoneTask("Paul", "123 4567");
		CodingTask databaseCode = new CodingTask("db");
		CodingTask interfaceCode = new CodingTask("gui");
		CodingTask logicCode = new CodingTask("logic");
		Collection<PhoneTask> phoneTasks = new ArrayList<PhoneTask>();
		Collection<CodingTask> codingTasks = new ArrayList<CodingTask>();
		Collection<Task> mondayTasks = new ArrayList<Task>();
		Collection<Task> tuesdayTasks = new ArrayList<Task>();			
		Collections.addAll(phoneTasks, mikePhone,paulPhone );
		Collections.addAll(codingTasks, databaseCode, interfaceCode, logicCode);
		Collections.addAll(mondayTasks, logicCode,paulPhone);
		Collections.addAll(tuesdayTasks, databaseCode, interfaceCode, paulPhone);	
		
		
		mondayTasks.add(new PhoneTask("soumen", "987 65143"));
		//mondayTasks.remove(paulPhone);	
		
		Set<Task> phoneAndMondayTasks = new TreeSet<Task>(mondayTasks);
		phoneAndMondayTasks.addAll(phoneTasks);

		
		for (Task task : phoneAndMondayTasks) {
			System.out.println(task.toString());
		}
		System.out.println("++++++++++++++++NavigableSet++++++++++++++++++++");
		NavigableSet<PriorityTask> priorityTasks = new TreeSet<PriorityTask>();
		priorityTasks.add(new PriorityTask(mikePhone, Priority.MEDIUM));
		priorityTasks.add(new PriorityTask(paulPhone, Priority.HIGH));
		priorityTasks.add(new PriorityTask(databaseCode, Priority.MEDIUM));
		priorityTasks.add(new PriorityTask(interfaceCode, Priority.LOW));
		priorityTasks.add(new PriorityTask(logicCode, Priority.LOW));
		
		
		for (PriorityTask priorityTask : priorityTasks) {
			
			System.out.println(priorityTask.toString());
		}
		
		System.out.println("+++++++++++++++++SortedSet+++++++++++++++++++");
		PriorityTask firstLowPriorityTask =	new PriorityTask(new EmptyTask(), Priority.LOW);
		PriorityTask firstMediumPriorityTask = new PriorityTask(new EmptyTask(), Priority.MEDIUM);
				SortedSet<PriorityTask> highAndMediumPriorityTasks = priorityTasks.tailSet(firstLowPriorityTask);
				SortedSet<PriorityTask> highAndMediumPriorityTasks1 = priorityTasks.subSet(firstMediumPriorityTask,firstLowPriorityTask);
				
		for (PriorityTask priorityTask : highAndMediumPriorityTasks) {
           
			System.out.println(priorityTask.toString());
		}
		
		System.out.println("++++++++++++++++++++SortedSet+++++++++++++++++");
		for (PriorityTask priorityTask : highAndMediumPriorityTasks1) {
	           
			System.out.println(priorityTask.toString());
		}
		
		
		
		System.out.println("++++++++++++++++ListIterator++++++++++++++++++++");
		
		
		List<String> l1 = new ArrayList<String>();
		l1.add("Element1");
		l1.add("Element2");
		l1.add("Element4");
		l1.add("Element3");
		
		ListIterator<String> lit = l1.listIterator(); 
		
		while(lit.hasNext()){
			System.out.println("Forward" + lit.next());
		}
		
		while(lit.hasPrevious()){
			System.out.println("Reverse" + lit.previous());			
			System.out.println("Previous Index is : " + lit.previousIndex());  
			System.out.println("Next Index is : " + lit.nextIndex());
		}
			for (int i = 0; i < l1.size(); i++) {
				
				System.out.println("Next element is : " + lit.next());
				lit.remove();
			}

			
          for (int i = 0; i < l1.size(); i++) {
				
				System.out.println("Final element is : " + lit.next());
				
			}
          
 System.out.println("++++++++++++++++Array Deque+++++++++++++++++++++");
          Queue<Task> taskQueue = new ArrayDeque<Task>();   	
      	
      	taskQueue.offer(mikePhone);
      	taskQueue.offer(paulPhone);
          
      	Task newtsk = taskQueue.poll();
      	if(newtsk!=null){
      		System.out.println(newtsk);
      	}
		
      	
      	System.out.println("++++++++++++++++Priority Qeque+++++++++++++++++++++");
      	
      	
      	Comparator<PriorityTask> priorityComparator = new Comparator<PriorityTask>()
      			{

					@Override
					public int compare(PriorityTask o1, PriorityTask o2) {
						// TODO Auto-generated method stub
						return o1.getPriority().compareTo(o2.getPriority());
					}
      		
      			};
      			
      			PriorityQueue<PriorityTask> priQueue = new PriorityQueue<>(10, priorityComparator);
      			priQueue.add(new PriorityTask(paulPhone, Priority.HIGH));
      			priQueue.add(new PriorityTask(mikePhone, Priority.HIGH));      			
      			priQueue.add(new PriorityTask(logicCode, Priority.HIGH));
      			
      			
      			PriorityTask priTask = priQueue.peek();
      			
      			System.out.println("Priority Item is : " + priTask);
      			
      			
      			int array[] = new int[5];
      			for (int i = 5; i > 0; i--)
      				array[5 - i] =i; 
      			Arrays.sort(array); 
      			for (int i = 0; i < 5; ++i)
      				System.out.print(array[i]);
      	
      			
      			System.out.println("Tree MAP : " + priTask);
      			Hashtable obj = new Hashtable();
      			obj.put("A", new Integer(1));
      			obj.put("B", new Integer(2));
      			obj.put("C", new Integer(3));
      			obj.remove(new String( "A"));
      			System.out.println(obj);
      			
      			System.out.println("TBit Set : " + priTask);
      			BitSet obj1 = new BitSet(5);
      			for (int i = 0; i < 5; ++i)
      				obj1.set(i); 
      			obj1.clear(2);
      			System.out.print(obj1);
	}
		
		
	
	
		
		
	}

