package com.honeywell.ven.api.event;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;

@XmlRootElement
public class RequestEvent extends BaseClass {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1011368004396259461L;
	private Long replyLimit;

	/**
	 * @return the replyLimit
	 */
	public Long getReplyLimit() {
		return replyLimit;
	}

	/**
	 * @param replyLimit the replyLimit to set
	 */
	public void setReplyLimit(Long replyLimit) {
		this.replyLimit = replyLimit;
	}

}
