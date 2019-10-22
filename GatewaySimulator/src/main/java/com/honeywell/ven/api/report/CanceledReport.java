package com.honeywell.ven.api.report;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;

@XmlRootElement
public class CanceledReport extends BaseClass{
	
	private Response response;
	private PendingReports pendingReports;
	
	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}
	/**
	 * @return the pendingReports
	 */
	public PendingReports getPendingReports() {
		return pendingReports;
	}
	/**
	 * @param pendingReports the pendingReports to set
	 */
	public void setPendingReports(PendingReports pendingReports) {
		this.pendingReports = pendingReports;
	}

}
