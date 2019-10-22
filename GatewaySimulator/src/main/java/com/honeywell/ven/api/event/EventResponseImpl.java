package com.honeywell.ven.api.event;

import com.honeywell.ven.api.EventResponse;
import com.honeywell.ven.api.OptType;

public class EventResponseImpl implements EventResponse {
	
	private String responseCode;
	
	private OptType optType;
	
	private String description;
	
	private String requestID;

	private String eventID;

	private long modificationNumber;
	

	public long getModificationNumber() {
		return modificationNumber;
	}
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public OptType getOptType() {
		return optType;
	}

	public void setOptType(OptType optType) {
		this.optType = optType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public void setModificationNumber(long modificationNumber) {
		this.modificationNumber = modificationNumber; 
	}
	
	

}
