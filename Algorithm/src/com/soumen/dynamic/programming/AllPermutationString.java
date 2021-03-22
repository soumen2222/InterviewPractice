package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.List;

public class AllPermutationString {
    public static List<String> allPermutationsRecursive(String s){
        if(s==null) return null;
        List<String> permutations = new ArrayList<>();
        if(s.length()==0) {
            permutations.add("");
            return permutations;
        }
       char first = s.charAt(0);
        String remainder = s.substring(1);
        List<String> words = allPermutationsRecursive(remainder);
        for(String word:words){
            for(int i=0; i<=word.length();i++){
                StringBuilder sb = new StringBuilder(word);
                sb.insert(i, first);
                permutations.add(sb.toString());
            }
        }
        return permutations;
    }

    public static List<String> allPermutations(String s){
        List<String> allCombinations = new ArrayList<>();
        allCombinations.add(Character.toString(s.charAt(0)));
        for(int i=1; i<s.length();i++){
            allCombinations = allPermutationsUtil(s.charAt(i),allCombinations);
        }
        return allCombinations;
    }

    public static List<String> allPermutationsUtil(Character c, List<String> comb){
        List<String> allCombinations = new ArrayList<>();
        for (String s:comb) {
            for(int i=0; i<=s.length();i++){
                StringBuilder sb = new StringBuilder(s);
                sb.insert(i, c);
                allCombinations.add(sb.toString());
            }
        }
        return allCombinations;
    }

    public static void main(String[] args){
        System.out.println(allPermutationsRecursive("abcd")+" Size :" + allPermutationsRecursive("abcd").size());
    }
}
