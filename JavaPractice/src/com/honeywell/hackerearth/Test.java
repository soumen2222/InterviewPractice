package com.honeywell.hackerearth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args){
    	ArrayList<String> arr = new ArrayList<String>(
    		    Arrays.asList("A","B","C","D","E","F"));
    	ArrayList<String> result = new ArrayList<String>();
    	
        String[] arr1 = {"A","B","C","D","E","F"};
        combinations(arr, 3, 0, result);
    }

    
    public static void combinations(ArrayList<String> arr, int len, int startPosition, ArrayList<String> result){
        if (len == 0){
        	System.out.println(".............");
        	for (String circle : result) {
				System.out.print(circle);
			}
        	
            return;
        }       
        for (int i = startPosition; i <= arr.size()-len; i++){
        	if(result.size()>=3){
            result.set((3-len),arr.get(i)) ;}
        	
           // result.get(result.size() - len) = arr.get(i);
        	
        	else{
        		 result.add((3-len),arr.get(i)) ;
        	}
            combinations(arr, len-1, i+1, result);
        }
    }     
    
    
    static void combinations2(String[] arr, int len, int startPosition, String[] result){
        if (len == 0){
            System.out.println(Arrays.toString(result));
            return;
        }       
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = arr[i];
            combinations2(arr, len-1, i+1, result);
        }
    }       
}