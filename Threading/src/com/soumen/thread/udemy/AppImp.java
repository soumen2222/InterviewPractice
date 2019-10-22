package com.soumen.thread.udemy;

 class Run implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Hello "+ i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	 
 }
 
 public class AppImp
 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Thread T1 = new Thread(new Run());
		Thread T2 = new Thread(new Run());
		T1.start();
		T2.start();
		
	}

}
