package com.qualitylogic.openadr.core.bean;

import com.qualitylogic.openadr.core.signal.OptTypeType;

public class CreatedEventBean {
	String venID;
	String requestID;
	String eventID;
	OptTypeType optType;
	Long modificationnumber;

	public Long getModificationnumber() {
		return modificationnumber;
	}

	public void setModificationnumber(Long modificationnumber) {
		this.modificationnumber = modificationnumber;
	}

	public String getVenID() {
		return venID;
	}

	public void setVenID(String venID) {
		this.venID = venID;
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

	public OptTypeType getOptType() {
		return optType;
	}

	public void setOptType(OptTypeType optType) {
		this.optType = optType;
	}

}
