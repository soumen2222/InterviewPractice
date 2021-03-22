package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.List;

public class FindPath {

    public static int findPath(int xPos, int yPos){
        int[][] tabArray = new int[yPos+1][xPos+1];
        tabArray[1][1]=1;
        for(int i=0; i<=yPos ;i++){
            for(int j=0; j<=xPos;j++){
                if(j-1>=0){
                    tabArray[i][j]+= tabArray[i][j-1];
                }
                if(i-1>=0){
                    tabArray[i][j]+= tabArray[i-1][j];
                }
            }
        }
        return tabArray[yPos][xPos];
    }


    public static List<List<String>> showPath(int xPos, int yPos){
        List<List<String>>[][] tabArray = new List[yPos+1][xPos+1];

        for(int i=0; i<=yPos ;i++) {
            for (int j = 0; j <= xPos; j++) {
                tabArray[i][j] = new ArrayList<>();
            }
        }
        String pos = "(1"+","+"1)";
        List<List<String>> finalList = new ArrayList<>();
        List<String> posList = new ArrayList<>();
        posList.add(pos);
        finalList.add(posList);
        tabArray[1][1]=finalList;
        for(int i=0; i<=yPos ;i++){
            for(int j=0; j<=xPos;j++){
                List<List<String>> listToBeSet = tabArray[i][j];
                if(j-1>=0){
                    List<List<String>> lists = tabArray[i][j - 1];
                    setList(i, j, listToBeSet, lists);
                }
                if(i-1>=0){
                    List<List<String>> lists = tabArray[i-1][j];
                    setList(i, j, listToBeSet, lists);
                }
            }
        }
        return tabArray[yPos][xPos];
    }

    private static void setList(int i, int j, List<List<String>> listToBeSet, List<List<String>> lists) {
        for (List<String> list:lists) {
            List<String> newList = new ArrayList<>();
            newList.addAll(list);
            newList.add( "("+ i + ","+j+ ")");
            listToBeSet.add(newList);
        }
    }

    public static void main(String[] args){
        System.out.println(findPath(3,3));
        System.out.println(showPath(3,3));
    }


}
