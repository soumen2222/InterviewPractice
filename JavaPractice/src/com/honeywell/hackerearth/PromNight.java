package com.honeywell.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromNight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		   InputStreamReader isr = new InputStreamReader(System.in);
		   BufferedReader br = new BufferedReader(isr);
		   
	    //number of test cases
		int noOfInputs = 0;
		String values = null;
		List<Integer> boyslist = new ArrayList<Integer>();
		List<Integer> girlslist = new ArrayList<Integer>();
		
		
		try {
			noOfInputs = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   for(int i=0;i<noOfInputs;i++)
		    {   
			   boyslist = new ArrayList<Integer>();
			   girlslist = new ArrayList<Integer>();
			   
			   try {
				values = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    	 String[] split = values.split(" ");
			            int boys = Integer.parseInt(split[0]);
			        	int girls = Integer.parseInt(split[1]);
			            String boysHeight = null;
				try {
					boysHeight = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       String[] splitboys = boysHeight.split(" ");
			       
			       for(int j =0; j<splitboys.length;j++){
			    	   boyslist.add(Integer.parseInt(splitboys[j]));
			       }
			       
			       		        	
			        	   String girlsHeight = null;
							try {
								girlsHeight = br.readLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						       String[] splitgirls = girlsHeight.split(" ");
						       
						       for(int j =0; j<splitgirls.length;j++){
						    	   girlslist.add(Integer.parseInt(splitgirls[j]));
						       }
						       
						       String result = PromNightfeasibibilty(boyslist,girlslist);
								System.out.println(result);    	
		    	
		    }             
		
	  

	}

	private static String PromNightfeasibibilty(List<Integer> boyslist, List<Integer> girlslist) {
		
		Collections.sort(boyslist);
		Collections.sort(girlslist);
		int count = 0;
		int x =0;
		for (Integer girldata : girlslist) {
			
			if(x==boyslist.size())
			{
				break;
			}
			if(girldata<boyslist.get(x)){
				x++;
			}
			
			
		}
		if(x== boyslist.size() )
			return "YES";
			else
			return "NO";
		
		// TODO Auto-generated method stub
		
	}
	
}