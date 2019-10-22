package com.soumen.thread.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memorizer3<A, V> implements Computable<String, BigInteger>{
	
	private final Map<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
	
	private final Computable<A, V> c;
	
	public Memorizer3(Computable<A, V> c)
	{
		this.c =c;
	}

	
	public synchronized V compute (final A arg) throws InterruptedException
	{
		Future<V> f = cache.get(arg);
		
		if(f==null)
		{
			Callable<V> eval = new Callable<V>()
					{
						@Override
						public V call() throws InterruptedException {
							// TODO Auto-generated method stub
							return c.Compute(arg);
						}
					};
					
					FutureTask<V> ft = new FutureTask<V>(eval);
					f=ft;
					cache.put(arg,ft);
					ft.run();					
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		}
		return null;
	}



	@Override
	public BigInteger Compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		return new BigInteger(arg);
	}

}
