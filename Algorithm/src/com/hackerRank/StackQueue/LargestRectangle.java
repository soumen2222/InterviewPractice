package com.hackerRank.StackQueue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class LargestRectangle {

	 private static final Scanner scanner = new Scanner(System.in);

	    public static void main(String[] args) throws IOException {
	        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Data\\data.txt"));

	        int n = scanner.nextInt();
	        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

	        int[] h = new int[n];

	        String[] hItems = scanner.nextLine().split(" ");
	        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

	        for (int i = 0; i < n; i++) {
	            int hItem = Integer.parseInt(hItems[i]);
	            h[i] = hItem;
	        }

	        long result = getMaxArea(h);

	        bufferedWriter.write(String.valueOf(result));
	        bufferedWriter.newLine();

	        bufferedWriter.close();

	        scanner.close();
	    }
	    
	    static long largestRectangle(int[] h) {	    	
	    	
	    	long maxValue =0;
	    	
	    	for(int i =0; i <h.length ; i++)
	    	{
	    		int minValue = h[i];
	    		for(int j =i; j <h.length ; j++)
	    		{
	    			minValue = Math.min(minValue, h[j]);
	    			long value = (j-i+1) * (long) minValue;
	    			maxValue = Math.max(value, maxValue);
	    		}    		
	    		
	    	}
			return maxValue;

	    }

	    
	  static long getMaxArea(int hist[]){
       
        Stack<Integer> s = new Stack<>();
         
        int max_area = 0; // Initialize max area
        int tp;  // To store top of stack
        int area_with_top; // To store area with top bar as the smallest bar
      
        // Run through all bars of given histogram
        int i = 0;
        while (i < hist.length)
        {
            // If this bar is higher than the bar on top stack, push it to stack
            if (s.empty() || hist[s.peek()] <= hist[i])
                s.push(i++);
      
            // If this bar is lower than top of stack, then calculate area of rectangle 
            // with stack top as the smallest (or minimum height) bar. 'i' is 
            // 'right index' for the top and element before top in stack is 'left index'
            else
            {
                tp = s.peek();  // store the top index
                s.pop();  // pop the top
      
                // Calculate the area with hist[tp] stack as smallest bar
                area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);
      
                // update max area, if needed
                if (max_area < area_with_top)
                    max_area = area_with_top;
            }
        }
      
        // Now pop the remaining bars from stack and calculate area with every
        // popped bar as the smallest bar
        while (s.empty() == false)
        {
            tp = s.peek();
            s.pop();
            area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1);
      
            if (max_area < area_with_top)
                max_area = area_with_top;
        }
        
        return max_area;
      
	    }

		
}
