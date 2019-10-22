package com.soumen.coding.challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HashTable_RansomNote {
    Map<String, Integer> magazineMap;
    Map<String, Integer> noteMap;
    
    public HashTable_RansomNote(String magazine, String note) {
        
    	String[] magazinewords = magazine.split(" ");
    	String[] notewords = note.split(" ");
    	magazineMap = new HashMap<String, Integer>();
    	noteMap = new HashMap<String, Integer>();
    	
    	for (int i =0; i < magazinewords.length ; i++)
    	{
    		
    		if(!magazineMap.containsKey(magazinewords[i]))
             {
    			magazineMap.put(magazinewords[i], 1);
             }
    		    		
    		magazineMap.put(magazinewords[i], magazineMap.get(magazinewords[i]) +1);
    		
    	}
    	
    	for (int i =0; i < notewords.length ; i++)
    	{
    		if(!noteMap.containsKey(notewords[i]))
            {
    			noteMap.put(notewords[i], 1);
            }
   		    		
    		noteMap.put(notewords[i], noteMap.get(notewords[i]) +1);
    		
    	}
    	
    }
    
    public boolean solve() {
    	
    	for (Map.Entry<String, Integer> entry : noteMap.entrySet())
    	{    		
    		if(magazineMap.get(entry.getKey()) < entry.getValue() )
    		{
    			return false;    			
    		}
    	   
    	}
        
    	return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        
        // Eat whitespace to beginning of next line
        scanner.nextLine();
        
        HashTable_RansomNote s = new HashTable_RansomNote(scanner.nextLine(), scanner.nextLine());
        scanner.close();
        
        boolean answer = s.solve();
        if(answer)
            System.out.println("Yes");
        else System.out.println("No");
      
    }
}
