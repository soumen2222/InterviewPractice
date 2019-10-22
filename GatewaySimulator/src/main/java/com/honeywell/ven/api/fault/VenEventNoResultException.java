package com.honeywell.ven.api.fault;

public class VenEventNoResultException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VenEventNoResultException(String message){
		super(message);
	}
	
	public VenEventNoResultException(String message, Exception ex){
		super(message, ex);
	}
}
