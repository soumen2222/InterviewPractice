package com.soumen.dynamic.programming;

public class MagicIndex {

    public static int findMagicIndex(int[] array){
        return findIndex(0,array.length-1,array);
    }

    private static int findIndex(int startIndex , int endIndex, int[] array){

        if((endIndex < startIndex) || startIndex <0 || endIndex >=array.length) return -1;
        int mid = (startIndex +endIndex)/2;
        if(array[mid]==mid) return mid;
        if(array[mid] > mid){
            return findIndex(startIndex,mid-1,array);
        }else{
            return findIndex(mid+1,endIndex,array);
        }
    }

    public static void main( String[] args){
        System.out.println(findMagicIndex(new int[]{-40,-20,-1,1,2,3,5,6,8,12,13}));
    }
}
