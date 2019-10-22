package com.courseera.algorith.toolbox.week3;



import java.util.*;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here
        String result = "";     
     
        List<String> list = new ArrayList<String>(Arrays.asList(a));        
      
        while (!list.isEmpty())
        {
        	int maxDigit = 0;
        	int maxindex =0;
        	int i = 0;
        	for (int y =0; y < list.size() ;y++)
        	{
        		if( isGreaterEqual(Integer.parseInt(list.get(y)),maxDigit))
       		   {
       			   maxDigit = Integer.parseInt(list.get(y));
       			   maxindex = i;
       		   }
         		i++;
        	}
        	list.remove(maxindex);        	
        	result = result + Integer.toString(maxDigit);
        }

        return result;
    
    }
    
    
    private static boolean isGreaterEqual(int digit, int maxDigit)
    {
		int digitMaxdigit =  Integer.parseInt(Integer.toString(digit)+  Integer.toString(maxDigit)) ;
		int maxDigitDigit =  Integer.parseInt(Integer.toString(maxDigit)+  Integer.toString(digit)) ;		
    	if(digitMaxdigit > maxDigitDigit)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}  		
    	
    }

    
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

