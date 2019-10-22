package com.soumen.coding.challenge.hashmap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TwoStrings {

    static String twoStrings(String s1, String s2) {
    	s1=sort(s1);
    	s2=sort(s2);    	
    	for(int i=0 ,j=0; ((i < Math.min(s1.length(),s2.length())) &&  (j < Math.min(s1.length(),s2.length()))) ;){
    		if(s1.charAt(i)==s2.charAt(j)) {
    			return "YES";
    		}    		
    		if(s1.charAt(i)> s2.charAt(j)) {
    			j++;
    		}else {
    			i++;
    		}
    	}
           return "NO";
    }
    
   

	private static String sort(String s1) {
    	
    	char[] ch = s1.toCharArray();
    	Arrays.sort(ch); 
    	return new String(ch);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\E442750\\text.txt"));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String s1 = scanner.nextLine();

            String s2 = scanner.nextLine();

            String result = twoStrings(s1, s2);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }

}










