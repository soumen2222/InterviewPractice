package com.honeywell.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MaxDist {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		//Number of speeds
		int N = Integer.parseInt(str);
		
		String temp[];
		Double Speed[] = new Double[N];
		Double Consumption[] = new Double[N];
		
		// Get all speed values
		
		str = br.readLine();
		temp = str.split(" ");
		for (int i = 0; i < N; i++) {			
			Speed[i] = Double.parseDouble(temp[i]);				
		}
		
		// Get all Consumption values
		str = br.readLine();
		temp = str.split(" ");
		
		for (int i = 0; i < N; i++) {			
			Consumption[i] = Double.parseDouble(temp[i]);				
		}
		
		//Get total fuel
		
		 str = br.readLine();
		 Double Fuel = Double.parseDouble(str);
		 
		 
		 
		 // Calculation of Max value
		 
		 Double max = 0.0;
		 double maxSpeed=0.0;
				
		 for (int i = 0; i < N; i++) {	
			 if (Consumption[i]>0.0){
			 maxSpeed =	(Speed[i]* Fuel)/Consumption[i];}
			 
			 if (maxSpeed> max){
				 max = maxSpeed;
			 }
			 
			}
		 
		
		 BigDecimal fd = new BigDecimal(max);
		 BigDecimal output = fd.setScale(3, RoundingMode.DOWN);
		 System.out.println(output);
	}

}
