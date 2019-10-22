package com.honeywell.hackerearth.honeywellexam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProfessorVer2 {

	
	public static void main(String[] args) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str;
		try {
			str = br.readLine();
		
		//Number of speeds
		int N = Integer.parseInt(str);
		
		String temp[];
		Integer origArray[] = new Integer[N+5];
		
		
		// Get all speed values
		
		str = br.readLine();
		temp = str.split(" ");
		for (int i = 1; i <= N; i++) {			
			origArray[i] = Integer.parseInt(temp[i-1]);				
		}
		
		// Get all Consumption values
		str = br.readLine();
		
		int Q = Integer.parseInt(str);
		int a[] = new int[N+5];			
		for (int i = 1; i <= Q; i++) {
			 str = br.readLine();
			temp = str.split(" ");
			
			for (int b =Integer.parseInt(temp[0]); b<=Integer.parseInt(temp[1]) ; b++)
			{
				a[b]= a[b]+1;
			}
								
		}
		
		
		for (int i = 1; i <= N/2; i++)
		{    
			a[i] = a[i]+a[N-i+1];
		}
		
		for (int i = 1; i <= N/2; i++)
		{    
			
			if(a[i]==1)
			{
				int beforeelement = origArray[i];
				int afterelement = origArray[N -i+1];
				origArray[i]=afterelement;
				origArray[N -i+1] =beforeelement;
			}
			
		}
			
		
		
		for (int i = 1; i <= N; i++) {			
			System.out.print(origArray[i]+" ");				
		}
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
			
}
