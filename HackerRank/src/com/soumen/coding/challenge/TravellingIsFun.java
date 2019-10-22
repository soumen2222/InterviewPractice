package com.soumen.coding.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TravellingIsFun {

    private static Map<Integer, Set<Integer>> adj = new HashMap<>();
    private static boolean[] marked;   // marked[v] = has vertex v been marked?
    private static int[] id;           // id[v] = id of connected component containing v
    private static int[] size;         // size[id] = number of vertices in given component
    private static int count;          // number of connected components
    static int[] connectedCities(int n, int g, int[] originCities, int[] destinationCities) {
    	
    	int retVal[] = new int[originCities.length];
    	List<Integer> cities = new ArrayList<>();
    	for (int i=1;i<=n;i++) {
    		cities.add(i);
    		 adj.put(i, new HashSet<>());
    	}   	
    	
    	gcdGraph(g, cities);
    	cc(cities);
        for (int i=0;i<originCities.length;i++) {        	
        			if(connected(originCities[i],destinationCities[i])) {
        				retVal[i]=1;
        			}else{
        				retVal[i]=0;
        			}
        }        
        return retVal;    	
    }

    public static void gcdGraph(int g, List<Integer> cities){  	
    
        int n = cities.size(); 
        for (int i = g + 1; i <= n; i++) {
	        for (int j = 2 * i; j <= n; j += i)
	         {
                    adj.get(cities.get(i-1)).add(cities.get(j-1));
                    adj.get(cities.get(j-1)).add(cities.get(i-1));
              }
           
        }
    }
    
    public static void cc(List<Integer> cities) {
        marked = new boolean[cities.size()+1];
        id = new int[cities.size()+1];
        size = new int[cities.size()+1];
        for (int v = 1; v <= cities.size(); v++) {
            if (!marked[v]) {
                dfs(v);
                count++;
            }
        }
    }
    
 // depth-first search for a Graph
    private static void dfs(int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        if(adjacentVertices(v)!=null&& !adjacentVertices(v).isEmpty()) {
        for (int w : adjacentVertices(v)) {
            if (!marked[w]) {
                dfs(w);
            }
        }
        }
    }

    private static boolean connected(int v, int w) {       
        return id[v] == id[w];
    }
    private static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    private static Set<Integer> adjacentVertices(int vertex){ 
       	return adj.get(vertex); }
  
   
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int g = in.nextInt();
        int originCities_cnt = in.nextInt();
        int[] originCities = new int[originCities_cnt];
        for(int originCities_i = 0; originCities_i < originCities_cnt; originCities_i++){
            originCities[originCities_i] = in.nextInt();
        }
        int destinationCities_cnt = in.nextInt();
        int[] destinationCities = new int[destinationCities_cnt];
        for(int destinationCities_i = 0; destinationCities_i < destinationCities_cnt; destinationCities_i++){
            destinationCities[destinationCities_i] = in.nextInt();
        }
        int[] res = connectedCities(n, g, originCities, destinationCities);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + (i != res.length - 1 ? "\n" : ""));
        }
        System.out.println("");


        in.close();
    }
}