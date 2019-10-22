package com.courseera.algorith.toolbox.week5;


import java.util.Scanner;

public class ChangeDP {
    private static int getChange(int money) {
    	
    	int[] minNumCoins = new int[money+1];
    	int[] coins = new int[]{1,3,4};   	
    	
    	for (int m =1; m<=money ;m++)
    	{
    		minNumCoins[m] = Integer.MAX_VALUE;
    		for(int j=0;j <coins.length ; j++)
    		{
    			if(m>=coins[j])
    			{
    				int numCoins = minNumCoins[m-coins[j]]+1;
    				if(numCoins<minNumCoins[m])
    				{
    					minNumCoins[m]=numCoins;
    				}
    			}
    		}
    	}
    	
    	return minNumCoins[money];    
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

