package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class RegisterReportRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private RegisterReport registerReport;
	private PushProfile pushProfile;
	
	public RegisterReport getRegisterReport() {
		return registerReport;
	}
	public void setRegisterReport(RegisterReport registerReport) {
		this.registerReport = registerReport;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
