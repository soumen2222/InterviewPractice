import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class UsingExecutorService {

   public static void main(String[] args) throws InterruptedException,ExecutionException,TimeoutException {
   final ExecutorService executorService = Executors.newCachedThreadPool();
   final long startTime = System.currentTimeMillis();
   final Future<Double> future =
		   executorService.submit(new PiCalculator());
   final double pi = future.get(10, TimeUnit.SECONDS);
   final long stopTime = System.currentTimeMillis();
   System.out.printf("Calculated Pi in %d milliseconds: %10.9f%n",
		   stopTime - startTime,pi);
   executorService.shutdown();
}
   

@Test
public void waitToComplete() throws InterruptedException {
final ExecutorService executor = Executors.newCachedThreadPool();
final CountDownLatch latch = new CountDownLatch(2);
executor.execute(new FiniteThreadNamePrinterLatch(latch));
latch.await();
System.out.println("Endof wait");
}
}

class PiCalculator implements Callable<Double> {
	public Double call() throws Exception {
		double currVal = 1.0;
		double nextVal = 0.0;
		double denominator = 1.0;
		for(int i = 0;
				Math.abs(nextVal - currVal) > 0.000000001d;
				denominator += 2.0, i++) {
			currVal = nextVal;
if(i % 2 == 1) {
nextVal = currVal - (1 / denominator);
} else {
nextVal = currVal + (1 / denominator);
}
}
return currVal * 4;
}
}


class FiniteThreadNamePrinterLatch implements Runnable {
final CountDownLatch latch;
 FiniteThreadNamePrinterLatch(final CountDownLatch latch) {
this.latch = latch;
}
@Override
public void run() {
for (int i = 0; i < 5; i++) {
System.out.println("Run from thread: " +
Thread.currentThread().getName());
}
latch.countDown();
}
}
