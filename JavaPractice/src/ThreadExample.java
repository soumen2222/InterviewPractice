import java.util.Arrays;

public class ThreadExample {
	
	
	public void method( int[] array) {
		Arrays.sort(array);  // sort the array
		
		int count=1;
		
		for(int i =0; i< array.length-1 ;i++) {
			
			if( array[i] < array[i+1]) {
				continue;
			}
			else {
				count++;
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		final Thread separateThread = new Thread(new ThreadPrinter());
		separateThread.start();
		for(int i = 0; i < 5; i++) {
			System.out.println("From the main thread: "
					+ Thread.currentThread().getName());
					Thread.sleep(1000);
					}
					}
}
class ThreadPrinter implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("From the new thread: " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

