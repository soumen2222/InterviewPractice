package com.honeywell.ven.api;

/**
 * EventResponse
 * @author sunil
 *
 */
public interface EventResponse {
	
	public String getResponseCode();
	
	public OptType getOptType();
	
	public String getDescription();
	
	public String getRequestID();
	
	public String getEventID();
	
	public long getModificationNumber();

}
