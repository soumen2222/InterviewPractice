package com.courseera.algorith.toolbox.week5;


import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {
    //Initialise the array
	  
    int insertion=0;
    int deletion=0;
    int match=0;
    int mismatch=0;
 
	  int[][] DP = new int[s.length()+1][t.length()+1];
	  
	  for (int i=0;i<=s.length();i++)
	  {
		  DP[i][0]=i;
	  }
	  for(int j =0; j<=t.length();j++)
	  {
		  DP[0][j]=j;		  
	  }
	  
	  for (int i =1; i<=s.length();i++)
	  {
		  for(int j= 1; j<=t.length();j++)
		  {
			   deletion=DP[i-1][j]+1;
			   insertion=DP[i][j-1]+1;
			   match=DP[i-1][j-1];
			   mismatch=DP[i-1][j-1]+1;			  
			   if(s.charAt(i-1)==t.charAt(j-1))
			   {
				   DP[i][j] = min(insertion,deletion,match);
			   }else
			   {
				   DP[i][j] = min(insertion,deletion,mismatch);
			   }
		  }
	  }
	  
    return DP[s.length()][t.length()];
  }
  private static int min(int insertion, int deletion, int match) {
	int min = Math.min(insertion, deletion);
	return Math.min(min,match);
	}
public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistance(s, t));
  }

}
