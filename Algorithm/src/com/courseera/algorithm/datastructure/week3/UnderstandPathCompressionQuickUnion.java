package com.courseera.algorithm.datastructure.week3;

/*
 *   This implementation is with array as tree similar to prioroty queue. Where find operation and union operations are logarithmic time.
 *   Smaller tree goes below.
 *   https://www.hackerearth.com/practice/notes/disjoint-set-union-union-find/
 */


public class UnderstandPathCompressionQuickUnion {

	private int[] id;
	private int[] rank;
	
	public UnderstandPathCompressionQuickUnion (int N)
	{
		id = new int[N];
		for(int i =0; i<N ; i++)
		{
			id[i]=i;
			rank[i]=0;
		}
	}
	
	private int root( int i)
	{
		if(i!=id[i])
		{
			id[i]=root(id[i]);
		}
		return id[i];
		
		/**
		  while(i!=id[i])
		  { id[i] =id[id[i]];
		  i=id[i];}
		   return i;
		 **/
	}
	
	public boolean connected(int p, int q)
	{
		return root(p)==root(q);
	}
	
	
	public void union (int p ,int q)
	{
		int i = root(p);
		int j = root(q);
		if (i != j) {
			if (rank[i] > rank[j])
				id[j] = i;
			else {
				id[i] = j;
				if (rank[i] == rank[j]) {
					rank[j]++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
