package com.EPL.PrimitiveTypes;

public class CountBits {

		
	public static short countBits(int x) {
		short numBits = 0;
		while (x != 0) {
		numBits += (x & 1) ;
		x >>>= 1;
		}
		return numBits;
	}
	
	
	public static long parity(long x) {
		long result = 0;
		while (x != 0) {
		result = result ^ (x & 1);
		x >>>= 1;}
	     return result;
		}
	
	public static long parity2(long x) {
		long result = 0;
		while (x != 0) {
		x = x & (x-1);
		result++;
		}
	     return result%2;
		}
	
	
	public static long swapBits(long i, long j , long num) {
		
		long ithposition = num & (1 << i);
		ithposition = (ithposition& ~(ithposition-1));
		long jthposition = num & (1 <<j);
		jthposition = (jthposition& ~(jthposition-1));
		
		if(ithposition>>i!=jthposition>>j) // Extract the lowest set bit 
		{
			//Then swap them
			num = num ^ (1 << i);
			num = num ^ (1 << j);			
		}
		
	     return num;
		}
	
	public static long reverse ( long num)
	{ 
		
		for (long i =0; i<=15 ;i ++)
		{
			num = swapBits(i,(31-i) ,num);
		}
		return num;
	}
	
	
	public static boolean isPowerOfTwo(int x)
    {
		
		
        // x will check if x == 0 and !(x & (x - 1)) will check if x is a power of 2 or not
        return (x & 1) == 0;
    }
	
	public static void main(String args[])
	{
		System.out.println(countBits(15));
		
		System.out.println(isPowerOfTwo(1021));
		System.out.println(parity2(16));
		System.out.println(swapBits(4,6,73));
		System.out.println(reverse(64));
	}
}
