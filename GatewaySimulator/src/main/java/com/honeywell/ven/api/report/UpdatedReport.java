package com.honeywell.ven.api.report;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;
@XmlRootElement
public class UpdatedReport extends BaseClass{
	
	private Response response;
	private CancelReport cancelReport;
	
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
	 * @return the cancelReport
	 */
	public CancelReport getCancelReport() {
		return cancelReport;
	}
	/**
	 * @param cancelReport the cancelReport to set
	 */
	public void setCancelReport(CancelReport cancelReport) {
		this.cancelReport = cancelReport;
	}

}
