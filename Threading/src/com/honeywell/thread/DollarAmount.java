package com.honeywell.thread;

public class DollarAmount {

	private int val;
	public DollarAmount(int val)
	{
		this.val =val;
	}
	public int compareTo(DollarAmount amount) {
		// TODO Auto-generated method stub
		if (amount.val >this.val)
			{return 1;}
		else
			return -1;
	}
}
