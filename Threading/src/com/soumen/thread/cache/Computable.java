package com.soumen.thread.cache;

public interface Computable<A, V> {
	V Compute (A arg) throws InterruptedException;


}
