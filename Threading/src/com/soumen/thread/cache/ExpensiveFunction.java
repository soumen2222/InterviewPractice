package com.soumen.thread.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

	@Override
	public BigInteger Compute(String arg) throws InterruptedException {
		// TODO Auto-generated method stub
		return new BigInteger(arg);
	}
	
	

}
