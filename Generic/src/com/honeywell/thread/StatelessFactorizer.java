package com.honeywell.thread;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.http.annotation.ThreadSafe;

@ThreadSafe
public class StatelessFactorizer implements Servlet {

	
	private final AtomicLong count = new AtomicLong(0);
	
	
	public long getCount() {
		return count.get();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
			
			BigInteger i = extractFromRequest(req);
			BigInteger[] factors = factor(i);
			count.incrementAndGet();
			encodeIntoResponse(resp, factors);
			
			List<String> one = Collections.synchronizedList(new ArrayList<String>());
			
			
			
		
		
	}

}
