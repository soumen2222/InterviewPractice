package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class fan{
	String Name;
	long Quotient;
}

public class Best_Player {

	private static List<fan> input1 = new ArrayList<fan>();
		
// Highest first
private static List<fan> sort(List<fan> input11)
{			
		for (int i=0; i<input11.size()-1 ;i++) {
			
			
			for (int j=0; j<input11.size()-i-1 ;j++) {
				
				if (input11.get(j).Quotient < input11.get(j+1).Quotient)
				{
					fan Var1 = input11.get(j);
					fan Var2 = input11.get(j+1);					
					input11.set(j+1, Var1);	
					input11.set(j, Var2);
				}				
			}
			
		}		
				
	   return input11;
	
}
	

private static List<fan> lexsort(List<fan> input11)
{			
		for (int i=0; i<input11.size()-1 ;i++) {
			
			
			for (int j=0; j<input11.size()-i-1 ;j++) {
				
				if (input11.get(j).Name.compareTo(input11.get(j+1).Name)>0)
				{
					fan Var1 = input11.get(j);
					fan Var2 = input11.get(j+1);					
					input11.set(j+1, Var1);	
					input11.set(j, Var2);
				}				
			}
			
		}		
				
	   return input11;
	
}

public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String temp[] = str.split(" ");
        int N = Integer.parseInt(temp[0]);
        int T = Integer.parseInt(temp[1]);
        
        for (int i = 0; i < N; i++) {
        	
        	String str1 = br.readLine();
            String temp1[] = str1.split(" ");   
            fan f = new fan();
            f.Name =temp1[0];
            f.Quotient= Long.parseLong(temp1[1]);            
        	input1.add(f);
        }
        
        List<fan> lexsorted = lexsort(input1);
       	List<fan> qsorted = sort(lexsorted);		
		
		 for (int i = 0; i < T; i++) {
			  System.out.println(qsorted.get(i).Name + " " + qsorted.get(i).Quotient);
	        }
		 
		 
	}

}
