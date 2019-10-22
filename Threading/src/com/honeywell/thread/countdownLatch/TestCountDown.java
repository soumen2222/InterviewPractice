package com.honeywell.thread.countdownLatch;

import java.util.concurrent.CountDownLatch;

public class TestCountDown {

	public static void main(String[] args) {
	
		Runnable task2 = () -> { System.out.println("Task is running"+ Thread.currentThread().getName()); };
		
		 
		try {
			System.out.println("Total Time" + timeTasks(10,task2));
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}

	}
	
	public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
		Thread t = new Thread() {
		     public void run() {
		        try {
		        	System.out.println("Start gate Await"+Thread.currentThread().getName());
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
		
		System.out.println("End of the gate"+Thread.currentThread().getName());
		long end = System.nanoTime();
		return end-start;
		}	

}
