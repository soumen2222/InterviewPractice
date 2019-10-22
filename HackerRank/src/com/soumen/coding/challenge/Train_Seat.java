package com.soumen.coding.challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Seatmap{
	
	private String pos;
	private int oppDiff;
	
	Seatmap(String pos,int oppDiff){
		this.pos=pos;
		this.oppDiff=oppDiff;
	}	

	public int getOppDiff() {
		return oppDiff;
	}

	public String getPos() {
		return pos;
	}

	
	
}

public class Train_Seat {
	
	static Map<Integer,Seatmap> maps = new HashMap<>();
	    public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int n = in.nextInt();
	        int a[] = new int[n];
	        maps.put(1, new Seatmap("WS", 11));
			maps.put(2, new Seatmap("MS", 9));
			maps.put(3, new Seatmap("AS", 7));
			maps.put(4, new Seatmap("AS", 5));
			maps.put(5, new Seatmap("MS", 3));
			maps.put(6, new Seatmap("WS", 1));
			maps.put(7, new Seatmap("WS", -1));
			maps.put(8, new Seatmap("MS", -3));
			maps.put(9, new Seatmap("AS", -5));
			maps.put(10, new Seatmap("AS", -7));
			maps.put(11, new Seatmap("MS", -9));
			maps.put(0, new Seatmap("WS", -11));
			
	        for(int a_i=0; a_i < n; a_i++){
	        	 findSeat(in.nextInt());
	        }
	      
	       
	      
	    }

		private static void findSeat(int a) {
			
			Seatmap seatmap = maps.get(a%12);
			
			System.out.println(a+seatmap.getOppDiff() +" " + seatmap.getPos());			
			
			
		}
	

}
