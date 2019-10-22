package com.soumen.coding.challenge;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.regex.*;

public class SherlockValidString {

    // Complete the isValid function below.
    static String isValid(String s) {
    	
    	int [] charCounts= getCharCounts(s);  
    	int maxElement =Integer.MIN_VALUE;
    	int minElement=Integer.MAX_VALUE;
    	int maxElementCount =0;
    	int minElementCount=0;
        Map<Integer, Integer> val = new HashMap<>();
        int totalCount=0;
        
        for(int i= 0; i<charCounts.length;i++) {
        	if(charCounts[i]==0)
        		continue;
        	maxElement= Math.max(maxElement, charCounts[i]);
        }
        
        for(int i= 0; i<charCounts.length;i++) {
        	if(charCounts[i]==0)
        		continue;
        	minElement= Math.min(minElement, charCounts[i]);
        }
        
        for(int i= 0; i<charCounts.length;i++) {
        	if(maxElement==charCounts[i]) {
        		maxElementCount++;
        	}
        	if(minElement==charCounts[i]) {
        		minElementCount++;
        	}
        }
    	
    	if(minElement==maxElement)
    		return "YES";
    	if(minElement==maxElement-1 && minElementCount==1)
    		return "YES";
    	if(maxElementCount==1 && maxElement==minElement+1)
    	    return "YES";
    	
    	
    	return "NO";
    }
    
    

    private static int[] getCharCounts(String s) {
    	int [] charLen = new int[26];
		for(int i=0; i<s.length();i++) {
			
			char charAt = s.charAt(i);
			int offset = (int) 'a';
			charLen[charAt-offset]++;
		}
    	
		return charLen;
	}

	private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\E442750\\text.txt"));

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
