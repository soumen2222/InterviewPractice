package com.honeywell.ven.services.messages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VenData {
	
	private String DRASVTNUrl;
	private String VenName;
	private String EventStatus;
	private String Status;
	private float EventSignal;
	private String EventID;
	private String VenID;
	
	
	public String getVenID() {
		return VenID;
	}
	public void setVenID(String venID) {
		VenID = venID;
	}
	public String getDRASVTNUrl() {
		return DRASVTNUrl;
	}
	public void setDRASVTNUrl(String dRASVTNUrl) {
		DRASVTNUrl = dRASVTNUrl;
	}
	
	public String getVenName() {
		return VenName;
	}
	public void setVenName(String venName) {
		VenName = venName;
	}
	public String getEventStatus() {
		return EventStatus;
	}
	public void setEventStatus(String eventStatus) {
		EventStatus = eventStatus;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	public float getEventSignal() {
		return EventSignal;
	}
	public void setEventSignal(float eventSignal) {
		EventSignal = eventSignal;
	}
	public String getEventID() {
		return EventID;
	}
	public void setEventID(String eventID) {
		EventID = eventID;
	}
    
    
    

    

}
