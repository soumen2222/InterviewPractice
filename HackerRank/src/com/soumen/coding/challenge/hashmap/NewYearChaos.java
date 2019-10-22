package com.soumen.coding.challenge.hashmap;

import java.util.Scanner;

public class NewYearChaos {

	private static final Scanner scanner = new Scanner(System.in);
	
	  // Complete the minimumBribes function below.
    static void minimumBribes(int[] q) {
     int count =0;
     boolean toChaotic= false;
     
     for (int i=0; i<q.length ;i++) {
  	   if((q[i] -(i+1)) >2) {
  		   toChaotic=true;  
  		 System.out.println("Too chaotic");
  		   break;
  	   }
     }
     if(!toChaotic) {
       for (int i=0; i<q.length-2 ;i++) {
    	   for(int j=i;j<i+2 ;j++) {
    	   if(q[j] > q[j+1]) {
    		   swap(q,j,j+1);
    		   count++;
    	    }
    	   } 	   
    	   
       }
       System.out.println(count);
     }       
   }

    private static void swap(int[] q, int j, int i) {
		int temp = q[j] ;
		q[j] =q[j+1] ;
		q[j+1]=temp;
		
	}
    


	public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}
