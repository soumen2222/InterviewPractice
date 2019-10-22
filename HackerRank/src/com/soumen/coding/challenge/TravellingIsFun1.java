package com.soumen.coding.challenge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TravellingIsFun1 {
	
	public static List<Integer> connectedCities(int n, int g, List<Integer> originCities, List<Integer> destinationCities) {
	    int[] root = new int[n + 1];
	    int[] ids = new int[n + 1];

	    for (int i = 0; i <= n; i++) {
	        root[i] = i;
	        ids[i] = 1;
	    }
	    
	    for (int i = g + 1; i <= n; i++)
	        for (int j = 2 * i; j <= n; j += i)
	            unionFind(j, i, root, ids);

	    List<Integer> res = new ArrayList<>(originCities.size());
	    Iterator<Integer> itSrc = originCities.iterator();
	    Iterator<Integer> itDest = destinationCities.iterator();

	    while (itSrc.hasNext() && itDest.hasNext())
	    	res.add(getRoot(itSrc.next(), root) == getRoot(itDest.next(), root) ? 1 : 0);

	    return res;
	}

	private static void unionFind(int a, int b, int[] root, int[] ids) {
	    int aRoot = getRoot(a, root);
	    int bRoot = getRoot(b, root);

	    if (aRoot == bRoot)
	        return;

	    if (ids[aRoot] < ids[bRoot]) {
	        root[aRoot] = root[bRoot];
	        ids[bRoot] += ids[aRoot];
	    } else {
	        root[bRoot] = root[aRoot];
	        ids[aRoot] += ids[bRoot];
	    }
	}

	private static int getRoot(int a, int[] root) {
	    while (a != root[a])
	        a = root[a];
	    return a;
	}
	
	 public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int n = in.nextInt();
	        int g = in.nextInt();
	        int originCities_cnt = in.nextInt();
	        List<Integer> originCities = new ArrayList<>();
	        for(int originCities_i = 0; originCities_i < originCities_cnt; originCities_i++){
	            originCities.add(in.nextInt());
	        }
	        int destinationCities_cnt = in.nextInt();
	        List<Integer> destinationCities = new ArrayList<>();	       
	        for(int destinationCities_i = 0; destinationCities_i < destinationCities_cnt; destinationCities_i++){
	            destinationCities.add(in.nextInt());
	        }
	        List<Integer> res = connectedCities(n, g, originCities, destinationCities);
	        for (int i = 0; i < res.size(); i++) {
	            System.out.print(res.get(i) + (i != res.size() - 1 ? "\n" : ""));
	        }
	        System.out.println("");


	        in.close();
	    }

}
