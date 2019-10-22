package com.honeywell.hackerearth.Nov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Hintdata{
    String color;
    List<Integer> cards;
}

public class Hibana {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Read the number of hints required to purchase 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int N = Integer.parseInt(str);
		
		// Read all the Hints
		String temp[];
		List<Hintdata> data = new ArrayList<Hintdata>();
		for (int i = 0; i < N; i++) {
			Hintdata d1 = new Hintdata();
			str = br.readLine();
			temp = str.split(" ");
			int N1 = Integer.parseInt(temp[0]);
			d1.color = temp[1];
			d1.cards = new ArrayList<Integer>();
			  for (int j = 0; j < N1; j++)
			      {
			    	  d1.cards.add(Integer.parseInt(temp[2+j]));			    	  
			      }
			   
			data.add(d1);
			
		}
		
		String[] finalcard = new String[5];		
		int[] counter = new int[]{0, 0, 0,0,0};
		List<String> colorList=new ArrayList<String>();
        for (Hintdata d : data) {
			
        	if(!colorList.contains(d.color)){
        		colorList.add(d.color);
        	
        	   for (int i = 0; i < d.cards.size(); i++) 
        	   {
				  
					   counter[d.cards.get(i)-1]  = counter[d.cards.get(i)-1] +1;		
					   
			   }
		}
    }
        
        if(N==0){
        	for(int i=0;i<5;i++){
        		finalcard[i] ="UNDEFINED";
        	}
        }
        else if(colorList.size()==1){
        	
        	for(int i=0;i<5;i++){
        		if(counter[i]!=0){
        			finalcard[i]="UNDEFINED";
        		}
        		else{
        			finalcard[i]="NO";
        		}
        	}
        	}
        else
        {
        
        for (int i = 0; i < counter.length; i++){
        	
        	if(counter[i]>1){
        		finalcard[i] = "YES";
        	}
        	else
        	{
        		finalcard[i] = "NO";
        	}
        	
        }
        }
        
        
        
        for (int i = 0; i < finalcard.length; i++) {
        	
        	switch(finalcard[i]==null?"":finalcard[i]    ) {
            case "YES" :
               System.out.println("YES"); 
               break;
            case "UNDEFINED" :
               System.out.println("UNDEFINED");
               break;                   	
               default :
               System.out.println("NO");
         }
        	
        	
		}
			       
}
}
