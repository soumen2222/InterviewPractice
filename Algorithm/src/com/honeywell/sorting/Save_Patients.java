package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Save_Patients {

	private static List<Integer> input1 = new ArrayList<Integer>();
	private static List<Integer> input2 = new ArrayList<Integer>();
// Lowest first
private static List<Integer> sort(List<Integer> input11)
{			
		for (int i=0; i<input11.size()-1 ;i++) {
			
			
			for (int j=0; j<input11.size()-i-1 ;j++) {
				
				if (input11.get(j) > input11.get(j+1))
				{
					Integer Var1 = input11.get(j);
					Integer Var2 = input11.get(j+1);					
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
        String line = br.readLine();
        int N = Integer.parseInt(line);
        
        String str = br.readLine();
		String temp[] = str.split(" ");
        
        for (int i = 0; i < N; i++) {
        	input1.add(Integer.parseInt(temp[i]));
        }
	
        
       str = br.readLine();
       String temp1[] = str.split(" ");
        
        for (int i = 0; i < N; i++) {
        	input2.add(Integer.parseInt(temp1[i]));
        }
	
		
		List<Integer> vaccines = sort(input1);
		List<Integer> midichlorians = sort(input2);
		
		String out ="Yes";
		
		 for (int i = 0; i < N; i++) {
	        	if(vaccines.get(i)<midichlorians.get(i))
	        	{
	        		out= "No";
	        		break;
	        	}
	        }
		 
		 System.out.println(out);
	}

}
