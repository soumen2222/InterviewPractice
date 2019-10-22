package com.courseera.algorith.toolbox.week2;
import java.util.*;

public class GCD {
  private static int gcd_naive(int a, int b) {
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

    System.out.println(gcd_naive(a, b));
  }
}
