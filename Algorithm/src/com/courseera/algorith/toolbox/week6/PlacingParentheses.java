package com.courseera.algorith.toolbox.week6;


import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
      //String to charcter array
    	char [] chars = exp.toCharArray();
    	int[] digit = new int[(chars.length +1)/2];
    	char[] op = new char[(chars.length -1)/2];
    	int a=0;
    	int b=0;
    	for (int i =0; i<chars.length ;i++)
    	{
    		if(i%2==0)
    		{
    			digit[a] = Character.getNumericValue(chars[i]);
    			a++;
    		}
    		else
    		{
    			op[b] = chars[i];
    			b++;
    		}
    	}
    	
    	long[][] minArray = new long[digit.length][digit.length];
    	long[][] maxArray = new long[digit.length][digit.length];
    	for(int i =0; i <digit.length ;i++)
    	{
    		// Initialise min max array
    		minArray[i][i]=digit[i];
    		maxArray[i][i]=digit[i];  		
       	}
    	int j=0;
    	for (int s= 0;s<digit.length-1 ;s++)
    	{
    		
    		for (int i =0 ; i<digit.length-s-1 ;i++ )
    		{
    			 j=i+s+1;
    			 minandMax(i,j,minArray,maxArray,op);
    			 
    		}
    	}
    	
      return maxArray[0][digit.length-1];
    }

    private static void minandMax(int i, int j, long[][] minArray, long[][] maxArray, char[] op) {
		
    	long min =Long.MAX_VALUE;
    	long max = Long.MIN_VALUE;
    	
    	for (int k = i; k<= j-1 ;k++)
    	{
    		long a = eval(maxArray[i][k],maxArray[k+1][j],op[k]);
    		long b = eval(maxArray[i][k],minArray[k+1][j],op[k]);
    		long c = eval(minArray[i][k],maxArray[k+1][j],op[k]);
    		long d = eval(minArray[i][k],minArray[k+1][j],op[k]);
    		
    		
    		min = minfunc(min,a,b,c,d);
    		max =maxfunc(max,a,b,c,d);
    	}
    
    	minArray[i][j]=min;
    	maxArray[i][j]=max;
	}

	private static long maxfunc(long max, long a, long b, long c, long d) {
		// TODO Auto-generated method stub
		
		long[] numbers = { max, a, b, c, d};
	    DoubleSummaryStatistics statistics = Arrays.stream(numbers).asDoubleStream().summaryStatistics();
		return (long) statistics.getMax();
	}

	private static long minfunc(long min, long a, long b, long c, long d) {
		long[] numbers = { min, a, b, c, d};
	    DoubleSummaryStatistics statistics = Arrays.stream(numbers).asDoubleStream().summaryStatistics();
		return (long) statistics.getMin();
	}

	private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}

