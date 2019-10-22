

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.junit.Test;



public class testJava3 {
	
	public static String transform(String string) {
		System.out.println("printing the numbre and thread" + Thread.currentThread());
		return string;
	}
   public static void main(String[] args) {
	   BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
	   
	   ReentrantLock lock = new ReentrantLock();
	   Condition c = lock.newCondition();
	  Integer i = new Integer(1);
	   ThreadPoolExecutor executors = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
	   
	   Callable<String> eval = new Callable<String>() {
		   public String call() throws InterruptedException {
		   return "Sucess";
		   }
		   };
	   
		   List<String> items = new ArrayList<>();
			items.add("A");
			items.add("B");
			items.add("CD");
			items.add("D");
			items.add("E");
			
			List<String> t2 = items.stream().filter(a ->a.length()>1).collect(Collectors.toList());
			
			
			items.parallelStream().map(testJava3::transform).forEach(System.out::println);
			
			List<String> t3 = items.stream().map(a ->a+"Soumen").collect(Collectors.toList());
			System.out.println("printing"+t3);

			
			System.out.println(Integer.MIN_VALUE);
			System.out.println(Integer.MAX_VALUE);
			//lambda
			//Output : A,B,C,D,E
			items.forEach(item->System.out.println(item));
			
			List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
			
			System.out.println("processor data"+ Runtime.getRuntime().availableProcessors());
			
			ForkJoinPool pool = ForkJoinPool.commonPool();
			
			System.out.println("ppol detals" + pool);
			
	   
   // create an empty array list with an initial capacity
   ArrayList<String> arrlist = new ArrayList<String>(5);

   // use add() method to add values in the list
   arrlist.add("G");

   String testdata =  "This Is Test";
 

   
   
   char[] stringtochararray = testdata.toCharArray();
   List<String> s = new ArrayList<>();
  
   
   stringtochararray.toString();
  
  
   System.out.println("Size of list: " + arrlist.size());

   // let us print all the values available in list
   for (String value : arrlist) {
   System.out.println("Value = " + value);
   }  
	
   // Removes first occurrence of "E"
   
   Boolean b = new Boolean(false);
   
   System.out.println("Now, Size of list: " + arrlist.size());
	
   // let us print all the values available in list
   for (String value : arrlist) {
   System.out.println("Value = " + value);
   }  
   
   Runnable nonCapturingLambda = () -> System.out.println("NonCapturingLambda");    
   Thread t = new Thread(nonCapturingLambda);
   t.start();
   }
   

static class Task implements Runnable {

    public void run() {

       try {
          Long duration = (long) (Math.random() * 5);
          System.out.println("Running Task! Thread Name: " +
             Thread.currentThread().getName());
          TimeUnit.SECONDS.sleep(duration);
          System.out.println("Task Completed! Thread Name: " +
             Thread.currentThread().getName());
       } catch (InterruptedException e) {
          e.printStackTrace();
       }
    }
 }

@Test
public void stringCreation() {
String helloString1 = "Hello World!";
String helloString2 = "Hello World!";
assertEquals(helloString1, helloString2);
assertTrue(helloString1 == helloString2);
}
 

@Test
public void stringChanges() {
final String greeting = "Good Morning, Dave";
final String substring = greeting.substring(4);
System.out.println(substring);

assertTrue(substring.equals("Good"));
assertFalse(greeting.equals(substring));
assertTrue(greeting.equals("Good Morning, Dave"));
}

@Test
public void checkIntern() {
		String constantString = "interned Baeldung";
		String newString = new String("interned Baeldung");
		assertFalse(constantString == newString);
		String internedString = newString.intern();
		assertTrue(constantString == internedString);
}
}

