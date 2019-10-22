package com.honeywell.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NameR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		   InputStreamReader isr = new InputStreamReader(System.in);
		   BufferedReader br = new BufferedReader(isr);
		   
		 int noOfInputs = 0;
		try {
			noOfInputs = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 String[] names = new String[noOfInputs];
		    for(int i=0;i<noOfInputs;i++)
		    {
		           try {
		            
					names[i] = br.readLine();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }             
		
	
		for (String word : names) {
			int noOfR = FindMostR(word);
			System.out.println(noOfR);
		}
		
		
		

	}
	
	private static int max( int a , int b){
		return  a>b?a:b;
	}
	
	private static int  FindMostR(String Word)
	{
		
		char[] cArray = Word.toCharArray();
		int [] numberedArray = new int[cArray.length];
		int i= 0;
		int maximum = 0;
		int count= 0;
		int sum =0;
		for (char c : cArray) {
			
			if( c=='R') 
				{numberedArray[i]= -1;			 
			   count++;}
			 else          //else if character at position X is 'K' then change it to 'R'
				 numberedArray[i]= 1;  
			i++;			
			
		}
		
		int [] tempArray = new int[numberedArray.length];
		for (int a= 0; a< numberedArray.length ; a++)
		{ 
			if(a>=1){
				tempArray[a] =  max (tempArray[a], numberedArray[a]+ tempArray[a-1]);
			}
			else
				tempArray[a] =  max (tempArray[a], numberedArray[a]);
			
			maximum = max(maximum, tempArray[a]);
			
		}
		if (count==numberedArray.length){
			count=count -1;
		}
		return maximum+count;
		
	}

}
