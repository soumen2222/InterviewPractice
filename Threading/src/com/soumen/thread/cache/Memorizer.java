package com.soumen.thread.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memorizer<A, V> implements Computable<String, BigInteger>{
	
	private final Map<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
	
	private final Computable<A, V> c;
	
	public Memorizer(Computable<A, V> c)
	{
		this.c =c;
	}

	
	public synchronized V compute (final A arg) throws InterruptedException
	{
		while (true) {
			Future<V> f = cache.get(arg);
			if (f == null) {
			Callable<V> eval = new Callable<V>() {
			public V call() throws InterruptedException {
			return c.Compute(arg);
			}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = ((ConcurrentHashMap<A, Future<V>>) cache).putIfAbsent(arg, ft);
			if (f == null) { f = ft; ft.run(); }
			}
			try {
			return f.get();
			} catch (CancellationException e) {
			cache.remove(arg);
			} catch (ExecutionException e) {
			e.getStackTrace();
			}
			}
	}



	@Override
	public BigInteger Compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		return new BigInteger(arg);
	}

}
