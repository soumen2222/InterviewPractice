package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class CreatedReportRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private CreatedReport createdReport;
	private PushProfile pushProfile;
	
	
	public CreatedReport getCreatedReport() {
		return createdReport;
	}
	public void setCreatedReport(CreatedReport createdReport) {
		this.createdReport = createdReport;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
