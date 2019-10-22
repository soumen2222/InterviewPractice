package com.courseera.algorith.toolbox.week2;


import java.util.*;

public class FibonacciHugeFast {
	
	
    private static long getPisanoPeriod(long m) {
        if (m <= 1)
            return m;

        long previous = 0;
        long current  = 1;
        long fib_num = previous + current;
        long length = 0;
             

        for (long i = 0; i < m*m; i++) {	
        	fib_num = (previous % m + current % m) % m;
        	previous = current;
            current = fib_num;
        	
        	if(previous ==0 && current == 1  )
        	{
        		length = i+1;
        		break;
        	}
            
        }
		return length;
    }

    
    private static long getFibonacciHuge(long n, long m) {
        if (n <= 1)
            return n;
        long rem = n % getPisanoPeriod(m);
        long previous = 0;
        long current  = 1;
        long fib_num = rem;

        for (long i = 1; i < rem; i++) {
        	fib_num = (previous % m +current % m) % m ;
            previous = current ;
            current = fib_num;
        }

        return fib_num;
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHuge(n, m));
    }
}

