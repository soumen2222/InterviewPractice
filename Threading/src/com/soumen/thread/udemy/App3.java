package com.soumen.thread.udemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base32;

public class App3
 {
	 public static ArrayList<Integer>  plusOne(ArrayList<Integer> A) {
		 int carry=1;
	        int sum=0;
	        for(int i=A.size()-1; i>=0;i--){
	            sum = A.get(i) + carry;
	            A.set(i,sum%10);
	            if(sum>=10){
	                carry = sum/10;
	            }else{
	                carry =0;
	            }
	        }
	        if(carry==1){
	            A.add(0,1);
	        }
	        while(!A.isEmpty() && A.get(0).equals(0)) {
	        	 A.remove(0);
	        }
	        
	        return A;
	    }
	
	public static void main(String[] args) {
		
		ArrayList<Integer> a = new ArrayList<>(Arrays.asList(0, 6, 0, 6, 4, 8, 8, 1));		
		plusOne(a);
		
		Base32 base32 = new Base32();
		System.out.println(base32.encodeAsString("test".getBytes()));
		
		List<String> mylist = Collections.synchronizedList(new ArrayList<String>());
		Thread t = new Thread();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				for (int i = 0; i < 10; i++) {
					System.out.println("Hello "+ i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
		
		
		t1.start();
	}


}
