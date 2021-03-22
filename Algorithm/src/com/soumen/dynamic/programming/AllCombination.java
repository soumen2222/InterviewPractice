package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllCombination {

    public static List<String> allCombinations(List<String> inputList){

        List<String> allList = new ArrayList<>();
        for(int i=0; i<inputList.size();i++){
            allList = allCombinationUtil(inputList.get(i),allList);
        }
        return allList;
    }

    private static List<String> allCombinationUtil(String newItem, List<String> allList) {
        List<String> newList = new ArrayList<>();
        newList.add(newItem);
        for(int i= 0; i<allList.size();i++){
            newList.add(String.join(":" ,allList.get(i),newItem));
        }
        allList.addAll(newList);
        return allList;
    }

    public static void main(String[] args){

        System.out.println(allCombinations(Arrays.asList("a","b","c","d")));
    }
}
