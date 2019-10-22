package com.courseera.algorith.toolbox.week3;




import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(long n) {
        List<Integer> summands = new ArrayList<Integer>();
        //write your code here
        long totaladded = 0;
        for( long i= 1 ; i <=n ; i++)
        {
        	long sum = (i)*(i+1)/2;
        	
        	if( sum <= n)
        	{
        		summands.add((int) i);
        		totaladded= totaladded + i;
        	}
        	else
        	{
        		summands.set(summands.size()-1, summands.get(summands.size()-1) + (int) (n-totaladded));
        		break;
        	}
        }
        
        return summands;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

