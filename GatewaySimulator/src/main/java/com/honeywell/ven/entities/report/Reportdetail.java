package com.honeywell.ven.entities.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.entities.common.BaseEntity;



@Entity
@NamedQueries({
	  @NamedQuery(name = "Reportdetail.findAll", query = "select o from Reportdetail o"),
	  @NamedQuery(name = "Reportdetail.findByvenid", query = "select o from Reportdetail o where o.Venid = :Venid"),
	  @NamedQuery(name = "Reportdetail.findByReportSpecifierId", query = "select o from Reportdetail o where o.ReportSpecifierId = :ReportSpecifierId"),})
@XmlRootElement
public class Reportdetail extends BaseEntity{
	
	private static final long serialVersionUID = 989187327603529074L;

	
	@Column(name="Venid")
	private String Venid;
	
	@Column(name="ReportRequestId")
	private String ReportRequestId;
	
	
	@Column(name="ReportSpecifierId")
	private String ReportSpecifierId;
	
	@Column(name="Vtnurl")
	private String Vtnurl;
	
	@Column(name="rID")
	private String rID;
	
	@Column(name="ReportCreated")
	private boolean ReportCreated;

	public String getVenid() {
		return Venid;
	}

	public void setVenid(String venid) {
		Venid = venid;
	}

	public String getReportRequestId() {
		return ReportRequestId;
	}

	public void setReportRequestId(String reportRequestId) {
		ReportRequestId = reportRequestId;
	}

	public String getReportSpecifierId() {
		return ReportSpecifierId;
	}

	public void setReportSpecifierId(String reportSpecifierId) {
		ReportSpecifierId = reportSpecifierId;
	}



	public String getVtnurl() {
		return Vtnurl;
	}

	public void setVtnurl(String vtnurl) {
		Vtnurl = vtnurl;
	}

	public String getrID() {
		return rID;
	}

	public void setrID(String rID) {
		this.rID = rID;
	}

	public boolean isReportCreated() {
		return ReportCreated;
	}

	public void setReportCreated(boolean reportCreated) {
		ReportCreated = reportCreated;
	}

	

}
