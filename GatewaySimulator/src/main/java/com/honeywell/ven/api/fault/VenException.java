package com.honeywell.ven.api.fault;

public class VenException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5793699277557480837L;

	public VenException(String message){
		super(message);
	}
	
	public VenException(String message, Exception ex){
		super(message, ex);
	}

}
