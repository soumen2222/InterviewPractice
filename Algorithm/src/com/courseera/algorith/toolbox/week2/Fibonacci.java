package com.courseera.algorith.toolbox.week2;

import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
	  int i = 1;
	  int a =0;
	  int b= 1;
	  int c= 0;
	  if (n <= 1)
          return n;
	  
	  while(i<n)
	  {
		  c=a+b;
		  a=b;
		  b=c;
		  i++;		  
	  }
    return c;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(calc_fib(n));
  }
}
