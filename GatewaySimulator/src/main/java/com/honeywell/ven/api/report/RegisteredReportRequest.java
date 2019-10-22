package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class RegisteredReportRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private RegisteredReport registeredReport;
	private PushProfile pushProfile;
	

	public RegisteredReport getRegisteredReport() {
		return registeredReport;
	}
	public void setRegisteredReport(RegisteredReport registeredReport) {
		this.registeredReport = registeredReport;
	}
	
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
