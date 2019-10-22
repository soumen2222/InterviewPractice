package com.courseera.algorithm.datastructure.week3;


/*
 *   This implementation is with array. Where find operation is constant time but union operation is linear time.
 */
public class UnderstandQuickFind {

	private int[] id;
	
	public UnderstandQuickFind (int N)
	{
		id = new int[N];
		for(int i =0; i<N ; i++)
		{
			id[i]=i;
		}
	}
	
	
	public boolean connected(int p, int q)
	{
		return id[p]==id[q];
	}
	
	public int find( int i)
	{
		return id[i];
	}
	
	public void union (int p ,int q)
	{
		if(!connected(p,q))
		{
			int pid = p;
			int qid = q;
			for (int i = 0; i < id.length; i++) {
                  if(id[i]==pid) id[i]=qid;                  
			}
		}
	}
	
	
	public void unionSmall (int p ,int q)
	{

		int pid = find(p);
		int qid = find(q);
		if (pid != qid) {
			int m = Math.min(pid, pid);
			for (int i = 0; i < id.length; i++) {
				if ((id[i] == pid)|| id[i]==qid)
					id[i] = m;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
