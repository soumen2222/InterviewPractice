package com.honeywell.ven.api.report;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;

@XmlRootElement
public class RegisterReport extends BaseClass{
	
	private List<Report> reportList;

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}
	
	

}
