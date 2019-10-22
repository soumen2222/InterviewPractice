package com.soumen.hackerrank;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class FBThread extends Thread {
	private static Object lock = new Object();
	protected static int current = 1;
	private int max;
	private Predicate<Integer> validate;
	private Function<Integer, String> printer;
	int X = 1;

public FBThread(Predicate<Integer> validate,Function<Integer, String> printer, int max) {

this.validate= validate;
this.printer= printer;
this.max = max;
}

public void run() {
     while (true) {
        synchronized (lock) {
          if (current > max) {
             return;}
           if (validate.test(current)) {
            System.out.println(printer.apply(current));
            current++;
           }
 }
 }
}


public static void main(String[] args) {
	
	int n = 100;
	Thread[] threads = {
			new FBThread(i ->i % 3 == 0 && i % 5==0, i -> "FizzBuzz", n),
			new FBThread(i ->i % 3 == 0 && i % 5!=0, i -> "Fizz", n),
			new FBThread(i ->i % 3 != 0 && i % 5==0, i -> "Buzz", n),
			new FBThread(i ->i % 3 != 0 && i % 5!=0, i -> Integer.toString(i), n)};
			
			for (Thread thread: threads) {
			thread. start();
			}
		
}
}