package com.soumen.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WeightedGraph {

	static class CountVertices{
		
		int count;
		List<Integer> path;
		CountVertices(int count, List<Integer> path ){
			this.count=count;
			this.path =path;
		}
	}
	
	 static class Edge {
	        int source;
	        int destination;
	        int weight;

	        public Edge(int source, int destination, int weight) {
	            this.source = source;
	            this.destination = destination;
	            this.weight = weight;
	        }
	    }
	 
	 static class Graph {
	        int vertices;
	        private static Map<Integer, Set<Edge>> adj = new HashMap<>();

	        Graph(int vertices) {
	            this.vertices = vertices;
	            //initialize adjacency lists for all the vertices
	            for (int i = 1; i <=vertices ; i++) {
	            	adj.put(i, new HashSet<>());
	            }
	        }

	        public void addEgde(int source, int destination, int weight) {
	           
	            adj.get(source).add(new Edge(source, destination, weight));
	            adj.get(destination).add(new Edge(destination, source, weight));	   
	            }
}
	 
	 public static void DFSUtil(int v,boolean visited[]) 
	    { 
	        // Mark the current node as visited and print it 
	        visited[v] = true; 
	        System.out.print(v+" "); 
	  
	        // Recur for all the vertices adjacent to this vertex 
	        Iterator<Integer> i = adj[v].listIterator(); 
	        while (i.hasNext()) 
	        { 
	            int n = i.next(); 
	            if (!visited[n]) 
	                DFSUtil(n, visited); 
	        } 
	    } 
	 
	 public static void DFS(int v) 
	    { 
	        // Mark all the vertices as not visited(set as 
	        // false by default in java) 
	        boolean visited[] = new boolean[v]; 
	  
	        // Call the recursive helper function to print DFS traversal 
	        DFSUtil(v, visited); 
	    } 
	 
	public static void BFS(Graph g, int src , int dst, int vertices) {
		 
		 boolean visited[] = new boolean[vertices+1];
		 Queue<Integer> queue = new LinkedList<Integer>();
		 visited[src]=true; 
	     queue.add(src);
	     Integer cur =src;
	     int count =0;
	     List<CountVertices> cv = new ArrayList<>();
	     List<Integer> path = new ArrayList<>();
	     path.add(cur);
	     
	     
	     
	     while(!queue.isEmpty()){
	    	 cur = queue.poll();
	    	 if(cur==dst) {
		    	 CountVertices e = new CountVertices(count, path);
				// Get the count and Vertices list.
	    		 cv.add(e );
	    		 
	    		 count =0;
	    		 path = new ArrayList<>();
	    		 cur = src;
	    		 path.add(cur);
		     }
	    	Set<Edge> set = g.adj.get(cur);
	    	for (Edge edge : set) {
	    		if (!visited[edge.destination]) 
                { 
                    visited[edge.destination] = true; 
                    queue.add(edge.destination); 
                    count = count + edge.weight;
                    path.add(edge.destination);
                } 
			}
	     }
		 
		 
	 }
	 
	 public static void main(String args[]) 
	    { 
	        Graph g = new Graph(5); 
	        g.addEgde(1,2,1); 
	        g.addEgde(2,3, 1); 
	        g.addEgde(3,4,1); 
	        g.addEgde(4,5,1); 
	        g.addEgde(5,1,3); 
	        g.addEgde(1,3,2); 
	        g.addEgde(5,3,1); 
	        WeightedGraph wg = new WeightedGraph();
	        wg.BFS(g, 1, 5, 5);
	       
	    } 
} 
