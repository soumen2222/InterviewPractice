package com.hackerRank.Arrays;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArrayManipulation {

    // Complete the arrayManipulation function below.
    static long arrayManipulation(int n, long[] a) {
    	 long maxValue=0;
    	 long sum=0;
    	 
    	 for (int i = 0; i <= n+1 ; i++) {
    		 sum+=a[i];
             maxValue=Math.max(sum,maxValue);
          }    	 
          return maxValue ;        
            
       }
    
 
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:/Data/out.txt"));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);
        long[] A= new long[n+2];

        for (int i = 0; i < m; i++) {
        	String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");            
            A[Integer.parseInt(queriesRowItems[0])] =  A[Integer.parseInt(queriesRowItems[0])]  + Long.parseLong(queriesRowItems[2]);
            A[Integer.parseInt(queriesRowItems[1]) +1] =  A[Integer.parseInt(queriesRowItems[1])+ 1]  + -1 * Long.parseLong(queriesRowItems[2]);
            
        }

        long result = arrayManipulation(n, A);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
