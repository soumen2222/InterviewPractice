package com.honeywell.thread.countdownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

class Horse{
	private int id;
	private long startTime;
	private double speed;
	private long endTime;
	
	Horse(int id, double speed )
	{
		this.id=id;
		this.speed=speed;
		this.startTime=0;
		this.endTime=0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
}

public class HorseRacingCountDown {

	public static void main(String[] args) {
		List<Runnable> tasks = new ArrayList<>();
		List<Horse> horses = new ArrayList<>();
		for(int i=0; i<10;i++) {
			horses.add(new Horse(i,15));
		}
		
		for(int i=0; i<10; i++) {	
			Horse h = horses.get(i);
			Runnable task = () -> { 
				System.out.println("Task is added for horse "+ h.getId() +" " + Thread.currentThread().getName());
				runExecution(h);				 
				};				
		   tasks.add(task);
		}
	 
		try {
			System.out.println("Total Time" + timeTasks(10,tasks,horses));
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}

	}
	


	public static long timeTasks(int nThreads, final List<Runnable> tasks, List<Horse> horses) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			Runnable task = tasks.get(i);
			System.out.println("Start of Thread "+i);
			
		Thread t = new Thread() {
		     public void run() {
		        try {	
		        	System.out.println("Start of Thread"+Thread.currentThread().getName());
		             startGate.await();
		               try {
		            	   System.out.println("Start gate Run"+Thread.currentThread().getName());
		                       task.run();
		              } finally {
		            	  System.out.println("Start gate CountDown"+Thread.currentThread().getName());
		                     endGate.countDown();
		                 }
		              } catch (InterruptedException ignored) { }
		        }
		     };
		 t.start();
		}
		long start = System.nanoTime();
		System.out.println("Before the start gate"+Thread.currentThread().getName());
		startGate.countDown();
		System.out.println("After the start gate"+Thread.currentThread().getName());
		endGate.await();
		long min =Integer.MAX_VALUE;
		int winnerHorseid =-1;
		for (Horse horse : horses) {
			if((horse.getEndTime()-horse.getStartTime()) < min) {
				min = horse.getEndTime()-horse.getStartTime();
				winnerHorseid=horse.getId();
			}			
		}
		System.out.println("Wiiner horse is " + winnerHorseid );
		System.out.println("End of the gate"+Thread.currentThread().getName());
		long end = System.nanoTime();
		return end-start;
		}	

	private static void runExecution(Horse h) {
		Random rand = new Random();
		
        int n = rand.nextInt(10) + 1;

        int _distance=0;
        h.setStartTime(System.nanoTime());
        System.out.println("Task is Running for horse "+
        		h.getId() +" Start Time " + h.getStartTime()+ Thread.currentThread().getName());
		while (_distance < 100)
        {
            try
            {
                Thread.sleep(n);
            }
            catch (Exception e)
            {
                e.printStackTrace();
              
            }
            _distance += 1;
        }

		 h.setEndTime(System.nanoTime());
		 System.out.println("Task is Finished for horse "+
					h.getId() +" end Time " + h.getEndTime()+ Thread.currentThread().getName());
	}
}
