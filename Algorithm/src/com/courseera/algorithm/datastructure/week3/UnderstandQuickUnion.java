package com.courseera.algorithm.datastructure.week3;

/*
 *   This implementation is with array as tree similar to prioroty queue. Where find operation and union operations are linear time in worst case.
 */


public class UnderstandQuickUnion {

	private int[] id;
	
	public UnderstandQuickUnion (int N)
	{
		id = new int[N];
		for(int i =0; i<N ; i++)
		{
			id[i]=i;
		}
	}
	
	private int root( int i)
	{
		while(i!=id[i]) i =id[i];
		return i;
	}
	
	public boolean connected(int p, int q)
	{
		return root(p)==root(q);
	}
	
	
	public void union (int p ,int q)
	{
		int i = root(p);
		int j = root(q);
		if(i!=j) id[i]=j;	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
