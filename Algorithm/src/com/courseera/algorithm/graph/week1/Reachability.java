package com.courseera.algorithm.graph.week1;

import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
	
	
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
         	
    	boolean visited[] = new boolean[adj.length+1];
    	return dfs(visited,adj,x,y);
    	
    }

 
    private static int dfs(boolean[] visited, ArrayList<Integer>[] adj, int src, int dst) {	    	
    	ArrayList<Integer> adjlist = adj[src];    	
	    for (Integer v : adjlist) {	    	
	    	if(src==dst) {
	    		return 1;
	    	}
			if(!visited[v]) {
				visited[v] =true;
				if(dfs(visited,adj,v,dst)==1) {
					return 1 ;
					}
			}	    	
		}	    
	    return 0;		
	}


	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x].add(y);
            adj[y].add(x);
        }
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.println(reach(adj, x, y));
    }
}

