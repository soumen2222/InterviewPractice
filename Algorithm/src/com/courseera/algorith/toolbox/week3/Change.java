package com.courseera.algorith.toolbox.week3;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Change {
    private static int getChange(int m) {
        //write your code here
    	List<Integer> denominations = new ArrayList<Integer>();
    	denominations.add(10);
    	denominations.add(5);
    	denominations.add(1);
    	int total = 0;
    	
    	for (Integer coins : denominations) {
    		
    		int quotient = m/coins;
    		int remainder = m % coins;
    		total= total + quotient ;
    		m= remainder;
		}
    	
        return total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

