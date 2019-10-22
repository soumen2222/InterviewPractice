package com.honeywell.ven.api.report;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PendingReports implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<String> reportRequestIdList;

	public List<String> getReportRequestIdList() {
		return reportRequestIdList;
	}

	public void setReportRequestIdList(List<String> reportRequestIdList) {
		this.reportRequestIdList = reportRequestIdList;
	} 

}
