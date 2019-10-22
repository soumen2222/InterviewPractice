
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ConnectedComponents {
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
    	
    	boolean visited[] = new boolean[adj.length];
    	int CCnum[] = new int[adj.length];
    	DFS(visited,CCnum,adj);
    	Arrays.sort(CCnum);
        int result = 1;
       
        for(int i=0; i<CCnum.length-1;i++) {
        	if((CCnum[i])!=CCnum[i+1]){
        		result++;
        	}
        }
        return result;
    }
    
    private static void DFS(boolean[] visited,  int[] CCnum,ArrayList<Integer>[] adj) {    	
    	int count=1;    	
    	for(int i=0;i<adj.length;i++) {
    		if(!visited[i]) {
    			explore(visited,CCnum,adj,i,count);
    		}
    		count=count+1;
    	}
    }
    
    private static void explore(boolean[] visited, int[] CCnum,  ArrayList<Integer>[] adj, int v , int count) {
    	visited[v] =true;
    	CCnum[v] = count;
    	
    	ArrayList<Integer> adjlist = adj[v];    	
	    for (Integer near : adjlist) {	    	
	    	if(!visited[near]) {
	    		explore(visited,CCnum,adj,near,count);
			}	    	
		}
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x-1].add(y-1);
            adj[y-1].add(x-1);
        }
        System.out.println(numberOfComponents(adj));
    }
}

