package com.courseera.algorithm.datastructure.week1;

import java.util.*;
import java.io.*;

class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
public class tree_height_soumen {
  		int n;
		int parent[];
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}

		int computeHeight() {
                      
			int maxHeight = 1;
			int minHeight = 1;
			int maxParentIndex = Integer.MIN_VALUE;
			int minParentIndex = Integer.MAX_VALUE;
			for (int vertex = 0; vertex < n; vertex++) {
				maxParentIndex =Math.max(maxParentIndex, parent[vertex]);	
				if( parent[vertex]>-1){
				minParentIndex =Math.min(minParentIndex, parent[vertex]);	}
			}
			
			do {
				maxParentIndex = parent[maxParentIndex];
				maxHeight++;
			}while(maxParentIndex!=-1);
				
			
			do {
				minParentIndex = parent[minParentIndex];
				minHeight++;
			}while(minParentIndex!=-1);
				
			return Math.max(maxHeight,minHeight);
		}
		
		static public void main(String[] args) throws IOException {
			
			tree_height_soumen tree = new tree_height_soumen();
			tree.read();
			System.out.println(tree.computeHeight());
		}
	}

