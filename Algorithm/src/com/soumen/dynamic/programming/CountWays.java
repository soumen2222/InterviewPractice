package com.soumen.dynamic.programming;

import java.util.Arrays;
import java.util.List;

public class CountWays {

    public static int countWays(int target , List<Integer> stepNos){
        int[] tabArray = new int[target+1];
        tabArray[0] =1;
        for(int i=1;i<=target;i++){
            for (Integer num:stepNos) {
                if(i-num>=0 && i-num<=target){
                    tabArray[i]+=tabArray[i-num];
                }
            }
        }

        return tabArray[target];
    }
    public static void main(String[] args) {
        System.out.println(countWays(7, Arrays.asList(1,2,3)));

    }
}
