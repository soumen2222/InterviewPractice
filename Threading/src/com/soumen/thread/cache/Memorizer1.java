package com.soumen.thread.cache;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Memorizer1<A, V> implements Computable<String, BigInteger>{
	
	private final Map<A,V> cache = new HashMap<A,V>();
	
	private final Computable<A, V> c;
	
	public Memorizer1(Computable<A, V> c)
	{
		this.c =c;
	}

	
	public synchronized V compute (A arg) throws InterruptedException
	{
		V result = cache.get(arg);
		
		if(result==null)
		{
			result = c.Compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
	@Override
	public BigInteger Compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		return new BigInteger(arg);
	}

}
