package com.soumen.hackerrank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.soumen.hackerrank.DiningPhilosopherProblem.ChopStick;

class Chopstick{
	
	private Lock lock =new ReentrantLock();
	
	public synchronized void  pickUp() {
		System.out.println("Picked up by " + Thread.currentThread().getName());
		//lock.lock();
	}
	
	public synchronized void putDown() {
		System.out.println("Put DOwn by " + Thread.currentThread().getName());
		//lock.unlock();
	}
}



public class DiningPhilosophers implements Runnable{
	
	private Chopstick left, right;
	
	DiningPhilosophers(Chopstick left, Chopstick right){
		this.left=left;
		this.right=right;
	}
	

	public void eat() {
		pickUp();
		chew();
		putDown();
	}
	
	public  void pickUp() {
		left.pickUp();
		right.pickUp();
	}
	
	public void chew() {
		System.out.println("Chew by " + Thread.currentThread().getName());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void putDown() {
		right.putDown();
		left.putDown();
		
	}


	
	public static void main(String[] args) {
		Chopstick[] chopSticks = new Chopstick[5];
		   for (int i = 0; i < 5; i++) {		       
			chopSticks[i] = new Chopstick();
		     }
		
		for(int i=0; i<5;i++) {
        Thread t1 = new Thread(new DiningPhilosophers(chopSticks[i], chopSticks[(i + 1) % 5]));
        t1.start();
		}
        
	}


	@Override
	public void run() {	
	  eat();
		
	}

}
