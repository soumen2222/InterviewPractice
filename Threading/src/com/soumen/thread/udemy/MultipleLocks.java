package com.soumen.thread.udemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipleLocks {
	
	
	private Random random = new Random();
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	
	
	public  void StageOne()
	{
		synchronized(lock1){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list1.add(random.nextInt(100));
		}
		
		
		
	}
	
	public void StageTwo()
	{
		synchronized(lock2){
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list2.add(random.nextInt(100));
		}
		
		
		
	}
	
	public void process()
	{
		for (int i = 0; i < 1000; i++) {
			StageOne();
			StageTwo();
		}
	}
	
	public void main()
	{
		System.out.println("Thread Starting");
		
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(new Runnable() {
			
			
			public void run() {
				
				process();
			}
		});
		
		

		
	Thread t2 = new Thread(new Runnable() {
			
			
			public void run() {
				
				process();
			}
		});
		
	t1.start();
	t2.start();
	
	
	//Wait for the thread to complete the task
	try {
		t1.join();
		t2.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	long end = System.currentTimeMillis();
	System.out.println("Time Taken is"+ (end -start));
	System.out.println("List1"+ list1.size() +", List2: " + list2.size());
	
	}
}
