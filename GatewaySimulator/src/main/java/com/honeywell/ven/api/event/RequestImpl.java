package com.honeywell.ven.api.event;

import com.honeywell.ven.api.Request;

/**
 * 
 * @author sunil
 *
 */
public class RequestImpl implements Request{

	private Long replyLimit;
	
	private String requestId;
	
	private String venID;

	public Long getReplyLimit() {
		return replyLimit;
	}

	public void setReplyLimit(Long replyLimit) {
		this.replyLimit = replyLimit;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getVenID() {
		return venID;
	}

	public void setVenID(String venID) {
		this.venID = venID;
	}
	
	
	
	
	
	
}
