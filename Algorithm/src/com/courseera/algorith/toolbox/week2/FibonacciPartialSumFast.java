package com.courseera.algorith.toolbox.week2;
import java.util.*;

public class FibonacciPartialSumFast {
    private static long getFibonacciPartialSumFast(long from, long to) {
                     
       	 return ((getFibonacciHuge(to+2) - getFibonacciHuge(from+1)) + 10 ) % 10 ;
       	
       	
       	
    }
    
    
    private static long getFibonacciHuge(long n) {
        if (n <= 1)
            return n;
        long rem = n % 60; // pisano period is 60
        long previous = 0;
        long current  = 1;
        long fib_num = rem;

        for (long i = 1; i < rem; i++) {
        	fib_num = (previous % 10 +current % 10) % 10 ;
            previous = current ;
            current = fib_num;
        }

        return fib_num;
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumFast(from, to));
    }
}

