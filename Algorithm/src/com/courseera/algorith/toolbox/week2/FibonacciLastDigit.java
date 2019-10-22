 package com.courseera.algorith.toolbox.week2;


import java.util.*;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1)
            return n;

        int previous = 0;
        int current  = 1;
        int fib_num =0;

        for (int i = 1; i < n; i++) {
        	fib_num = (previous+current) % 10 ;
            previous = current % 10 ;
            current = fib_num;
        }

        return fib_num;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = getFibonacciLastDigitNaive(n);
        System.out.println(c);
    }
}

