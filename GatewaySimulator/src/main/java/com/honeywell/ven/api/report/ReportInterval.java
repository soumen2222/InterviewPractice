package com.honeywell.ven.api.report;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportInterval implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String uId;
	private Date start;
	private Long duration;
	private List<? extends PayloadBase> payloadList;
	/**
	 * @return the uId
	 */
	public String getuId() {
		return uId;
	}
	/**
	 * @param uId the uId to set
	 */
	public void setuId(String uId) {
		this.uId = uId;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the duration
	 */
	public Long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	/**
	 * @return the payloadList
	 */
	
	/**
	 * @param payloadList the payloadList to set
	 */
	public void setPayloadList(List<? extends PayloadBase> payloadList) {
		this.payloadList = payloadList;
	}
	public List<? extends PayloadBase> getPayloadList() {
		return payloadList;
	}

}
