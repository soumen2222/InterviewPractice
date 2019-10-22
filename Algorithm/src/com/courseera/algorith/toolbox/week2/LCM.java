package com.courseera.algorith.toolbox.week2;


import java.util.*;

public class LCM {
  private static long lcm(int a, int b) {
	 //LCM* GCD = Product of Numbers;	  
	long lcm = ((long) a * b)/gcd(a,b); 
    return lcm;
  }
  
  private static long gcd(int a, int b) {
	    int current_gcd = 1;
	    
	    while(true)
	    {  
	    	int rem = a % b;
	    	a = b;
	    	b = rem;
	    	if( rem ==0)
	    	{
	    		current_gcd = Math.max(a, b);
	    		break;
	    	}
	    }
	    
	    return current_gcd;
	  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcm(a, b));
  }
}
