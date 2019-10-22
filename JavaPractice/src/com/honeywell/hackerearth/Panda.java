package com.honeywell.hackerearth;

public class Panda {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sel=0;
		int N=3;
		int [] items = new int[] {1, 2, 3};		
		int [] xorList = new int [128];
		
		for(int i=0; i<N ;i++){			
			int pandaXor = items[i];
			xorList[pandaXor]= xorList[pandaXor] +1;
					
			for(int j=i+1; j<N;j++){				
			 pandaXor = items[i]^ items[j];	
			 
			 xorList[pandaXor]= xorList[pandaXor] +1;
			 
			}
		}
		
		for (int i : xorList) {
			if (i>1){				
				sel++;
			}
			
		}
		System.out.println(sel);

	}

}
