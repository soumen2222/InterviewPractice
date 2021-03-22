package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeChange {

    public static int makeChange(int target , List<Integer> coins ,int currentCoin, Map<String,Integer> memo){
        if(memo.containsKey(target+","+currentCoin)) return memo.get(target+","+currentCoin);
        if(target<0) return 0;
        if(target==0) return 1;
        int noOfWays =0;
        for(int i=currentCoin;i<coins.size();i++){
            noOfWays+=makeChange(target-coins.get(i),coins,i,memo);
        }
       memo.put(target+","+currentCoin,noOfWays);
        return noOfWays;
    }

    public static int makeChangeTab(int target , List<Integer> coins ){
      int[] tabArray = new int[target+1];
      tabArray[0]=1;
      for (int i = 0; i < coins.size(); i++) {
            for (int j = 0; j < tabArray.length; j++) {
                if (j+coins.get(i) <=target ) {
                    tabArray[j+coins.get(i)] += tabArray[j];
                }
            }
        }
        return tabArray[target];
    }

    public static void main(String[] args){

        System.out.println("Tab:" + makeChangeTab(10, Arrays.asList(1,5,10)));
        System.out.println("Recursion:" + makeChange(10, Arrays.asList(1,5,10), 0,new HashMap<>()));
    }
}
