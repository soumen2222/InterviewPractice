package com.soumen.coding.challenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Pair{
    char c;
    int count;
    Pair(char c, int count){
        this.c =c;
        this.count=count;
    }
}

public class SpecialPalindrome {

    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
    	
    List<Pair> list = new ArrayList<>();
      char[] c = s.toCharArray();
      char cur = 0;
      long finalCount=0;
      for(int i =0; i<c.length;i++){
          
          if(s.charAt(i)!=cur){
        	  list.add(new Pair(s.charAt(i), 1));
              cur=s.charAt(i);
          }else{ 
        	  Pair p= list.get(list.size() - 1);
        	  p.count++;
        	  list.set(list.size()-1, p);
          }

      }
      System.out.println();

      for (Pair d : list) {
    	  finalCount =finalCount+ (d.count)*(d.count+1)/2;
	}
     
      for(int i =1; i<list.size()-1;i++) {
    	  if(list.get(i-1).c==list.get(i+1).c && list.get(i).count==1)
    	  finalCount =finalCount+ Math.min(list.get(i-1).count,list.get(i+1).count);
      }
    


return finalCount;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\TEMP\\new.txt"));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
