package com.honeywell.ven.api.report;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;

@XmlRootElement
public class RegisteredReport extends BaseClass{
	
	private Response response;
	private List<ReportRequest> reportRequestList;
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public List<ReportRequest> getReportRequestList() {
		return reportRequestList;
	}
	public void setReportRequestList(List<ReportRequest> reportRequestList) {
		this.reportRequestList = reportRequestList;
	}

}
