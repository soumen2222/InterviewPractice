package com.honeywell.hackerearth.Nov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;




public class Beatles {

	/**
	 * @param args
	 */
	
  private static long min =1;
  private static long[] result ;
	
	private static long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b; 
	        a = temp;
	    }
	    return a;
	}
	
	
	private static long lcm(long a, long b)
	{
	    return a * (b / gcd(a, b));
	}

	private static long lcm(long[] input)
	{
	    long result = input[0];
	    for(int i = 1; i < input.length; i++) result = lcm(result, input[i]);
	    return result;
	}
	
	static void combinationUtil(long[] arr, long[] data, int start, int end,
			int index, int r) {
		// Step2: Min value of LCM of all combination 
		
		if (index == r) {
		long lcm = lcm(data);
		
		if(lcm<min){
			
			min = lcm;
			// Store the value in a array 
			
			for (int j = 0; j < r; j++){
				result[j] =data[j];}
		}
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = arr[i];
			combinationUtil(arr, data, i + 1, end, index + 1, r);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Read all the data
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int T = Integer.parseInt(str);

		// Read all the Hints
		String temp[];
		str = br.readLine();
		temp = str.split(" ");
		long arr[] = new long[T];
		for (int i = 0; i < T; i++) {
			arr[i] = Long.parseLong(temp[i]);
			min = min*arr[i]; }
		
					
		//step 1:Find all combination of n-1 number 
		
		int end = arr.length;
		Set<Long> set = new HashSet<Long>();

		for(int i = 0; i < end; i++){
		  set.add(arr[i]);
		}
		
		
		long[] temparr = new long[set.size()];
		int c = 0;
		for(long x : set) temparr[c++] = x;	
		
		
		result = new long[set.size()-1];
		long data[] = new long[set.size()-1];
		combinationUtil(temparr, data, 0, temparr.length - 1, 0, set.size()-1);	
				
		boolean gotvalue;
		for (int i = 0; i < arr.length; i++){
			gotvalue = false;
			for (int j = 0; j < result.length; j++)
			{
				if(arr[i]==result[j]){
					gotvalue =true;
					break;
				}
			}
			if(!gotvalue){
				System.out.println(i+1);
				break;
			}
		
			
	}
		
	}

}
