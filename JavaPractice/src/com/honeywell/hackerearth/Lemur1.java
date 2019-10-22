package com.honeywell.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Lemur1 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException {
		
		//enter Banana
		int banana =47;		
		//enter Lemur
		int lemur =17;

	        //  open up standard input
	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	      String values = null;


	      try {
	         values = br.readLine();
	        String[] split = values.split(" ");
	        if(split.length == 2)
	        {
	        	banana = Integer.parseInt(split[0]);
	        	lemur= Integer.parseInt(split[1]);
	                }else{
	            //TODO handle error
	        }

	      } catch (IOException ioe) {
	         System.out.println("IO error!");
	         System.exit(1);
	      }
	
		
	Lemur1 algolemur = new Lemur1();
	int minumsteps= algolemur.HungryLemurs(lemur,banana)	;
	System.out.println(minumsteps );	
		

	}
	
	public int HungryLemurs(int K , int N )
	{
		int min = N+K;
					
			for (int j = 1; j <= (2*K); j++) {
				int rem =0;
				if(j>0){
				 rem = N%j;
				}
				
				if ( (j-rem) < rem)
				{
					rem = j-rem;
				}
				int x =  rem;
				int y = Math.abs(K-j);
				if ( min > (x+y))
				  min = x+y;
				}			
		
		return min;
		
	}	
		
}
