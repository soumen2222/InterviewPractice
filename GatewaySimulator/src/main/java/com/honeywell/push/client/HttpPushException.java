package com.honeywell.push.client;

public class HttpPushException extends Exception{

	
	/**
	 * Serial #
	 */
	private static final long serialVersionUID = 3757485525074231701L;

	public HttpPushException(){
		super();
	}
	
	public HttpPushException(String message){
		super(message);
	}

	
	public HttpPushException(String message, Exception e ){
		super(message, e);
	}
}
