package com.honeywell.hackerearth.Nov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class FruitShop {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Count required to purchase 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		String temp[] = str.split(" ");
		int costPerA = Integer.parseInt(temp[0]);
		int costPerB = Integer.parseInt(temp[1]);
		int costPerC = Integer.parseInt(temp[2]);
		
		// Quantity of fruits available
		str = br.readLine();
		temp = str.split(" ");
		int maxNumA = Integer.parseInt(temp[0]);
		int maxNumB = Integer.parseInt(temp[1]);
		int maxNumC = Integer.parseInt(temp[2]);
		
		str = br.readLine();
		int N = Integer.parseInt(str);
		
			
// Find the minimum cost and corresponding count;
		
		HashMap<Integer , Integer> hm = new HashMap<Integer , Integer>();
		// Put elements to the map
		hm.put(costPerA, maxNumA);
		hm.put(costPerB, maxNumB);
		hm.put(costPerC, maxNumC);
		
		
        int Arr[] = {costPerA,costPerB,costPerC};
		
		Arrays.sort(Arr);
		
		int min = Arr[0];
		int secondMin = Arr[1];
		int max = Arr[2];
		
		int nummin;
		int numsecondMin;
		int nummax;
		
		if(secondMin==min && secondMin==max ){
			nummin= maxNumA;
			numsecondMin= maxNumB;
			nummax = maxNumC;
		}
		else{
			nummin= hm.get(min);
			 numsecondMin= hm.get(secondMin);
			 nummax= hm.get(max);
		}
		
		 
		
		int x=0,y=0,z=0;
		int maxnumber=0;
		while ((x*min+y*secondMin+z*max) <=N)
		{
			
			if ((x+y+z)>maxnumber)
			{
				maxnumber=x+y+z;
			}
			
			if(x<nummin){x++;}
			else
				if (y<numsecondMin){y++;}
				else
					if(z<nummax){z++;}
			
			
			
		}
		
		System.out.println(maxnumber);
			       
}
}
