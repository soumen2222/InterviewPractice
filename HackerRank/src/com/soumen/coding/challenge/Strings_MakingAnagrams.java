package com.soumen.coding.challenge;
import java.util.Arrays;
import java.util.Scanner;
public class Strings_MakingAnagrams {
	
	private static int count =0;

    public static int numberNeeded(String first, String second) {
         char[] input1 = first.toCharArray();
         char[] input2 = second.toCharArray();
    	 
         for(int i =0; i < first.length() ; i ++ )
         {
         	 search(input1[i],input2);        	
         }
    	
         
    	return (first.length() + second.length() - (2*count) );
    }
    
    
  
    private static void search(char c, char[] input2) {
		
    	for(int i =0; i < input2.length ; i ++ )
        {
    		if(input2[i]==c)
    		{
    			count++;
    			input2[i]=Character.MIN_VALUE;
    			break;
    		}
        }
		
	}



	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(numberNeeded(a, b));
    }
}
