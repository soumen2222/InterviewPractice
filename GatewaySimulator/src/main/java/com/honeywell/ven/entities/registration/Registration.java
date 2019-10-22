package com.honeywell.ven.entities.registration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.honeywell.ven.entities.common.BaseEntity;




@Entity
@NamedQueries({
	  @NamedQuery(name = "Registration.findAll", query = "select o from Registration o"),
	  @NamedQuery(name = "Registration.findByvenid", query = "select o from Registration o where o.Venid = :Venid")})


public class Registration extends BaseEntity{
	
	private static final long serialVersionUID = 989187327603529074L;
	
	
	@Column(name="Venid")
	private String Venid;
	
	
	@Column(name="Vtnid")
	private String Vtnid;
	
	@Column(name="Vtnurl")
	private String Vtnurl;
	
	@Column(name="VenName")
	private String VenName;
	
	@Column(name="VenComStatus")
	private String VenComStatus;

	@Column(name="PollFrequency")
	private double PollFrequency;
	

	@Column(name="ReportFrequency")
	private double ReportFrequency;


	public String getVenid() {
		return Venid;
	}


	public void setVenid(String venid) {
		Venid = venid;
	}


	public String getVtnurl() {
		return Vtnurl;
	}


	public void setVtnurl(String vtnurl) {
		Vtnurl = vtnurl;
	}


	public String getVenName() {
		return VenName;
	}


	public void setVenName(String venName) {
		VenName = venName;
	}


	public double getPollFrequency() {
		return PollFrequency;
	}


	public void setPollFrequency(double pollFrequency) {
		PollFrequency = pollFrequency;
	}


	public double getReportFrequency() {
		return ReportFrequency;
	}


	public void setReportFrequency(double reportFrequency) {
		ReportFrequency = reportFrequency;
	}


	public String getVtnid() {
		return Vtnid;
	}


	public void setVtnid(String vtnid) {
		Vtnid = vtnid;
	}


	public String getVenComStatus() {
		return VenComStatus;
	}


	public void setVenComStatus(String venComStatus) {
		VenComStatus = venComStatus;
	}
	


		

}
