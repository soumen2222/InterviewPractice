package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class UpdateReportRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private UpdateReport updateReport;
	private PushProfile pushProfile;
	
	public UpdateReport getUpdateReport() {
		return updateReport;
	}
	public void setUpdateReport(UpdateReport updateReport) {
		this.updateReport = updateReport;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	

}
