package com.honeywell.hackerearth.honeywellexam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProfessorVer1 {

	
	public static void main(String[] args) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str;
		try {
			str = br.readLine();
		
		//Number of speeds
		int N = Integer.parseInt(str);
		
		String temp[];
		Integer origArray[] = new Integer[N];
		
		
		// Get all speed values
		
		str = br.readLine();
		temp = str.split(" ");
		for (int i = 0; i < N; i++) {			
			origArray[i] = Integer.parseInt(temp[i]);				
		}
		
		// Get all Consumption values
		str = br.readLine();
		
		int Q = Integer.parseInt(str);
		Integer a[][] = new Integer[Q][2];			
		for (int i = 0; i < Q; i++) {
			 str = br.readLine();
			temp = str.split(" ");
			a[i][0] = Integer.parseInt(temp[0]);
			a[i][1] = Integer.parseInt(temp[1]);
					
		}
		
		
		for (int i = 0; i < Q; i++)
		{    
			
			
			for (int j=a[i][0] ; j <= a[i][1]; j++)
			{
				if(a[i][1]==N-a[i][0]+1){
					break;
				}
				int beforeelement = origArray[j-1];
				int afterelement = origArray[N -j];
				origArray[j-1]=afterelement;
				origArray[N -j] =beforeelement;
				
			}
			
		}
		
		for (int i = 0; i < N; i++) {			
			System.out.print(origArray[i]+" ");				
		}
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
			
}
