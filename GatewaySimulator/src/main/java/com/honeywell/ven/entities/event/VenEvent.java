package com.honeywell.ven.entities.event;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import com.honeywell.ven.entities.common.BaseEntity;



@Entity
@NamedQueries({
	  @NamedQuery(name = "VenEvent.findAll", query = "select o from VenEvent o"),
	  @NamedQuery(name = "VenEvent.findByvenid", query = "select o from VenEvent o where o.Venid = :Venid")})


public class VenEvent extends BaseEntity{
	
	private static final long serialVersionUID = 989187327603529074L;
    
	
	@Column(name="Venid" , unique = true )
	private String Venid;
	
	@Column(name="EventID" )
	private String EventID;
	
	@Column(name="EventStatus")
	private String EventStatus;
	
	@Column(name="EventSignal")
	private float EventSignal;

	public String getVenid() {
		return Venid;
	}

	public void setVenid(String venid) {
		Venid = venid;
	}

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getEventStatus() {
		return EventStatus;
	}

	public void setEventStatus(String eventStatus) {
		EventStatus = eventStatus;
	}

	public float getEventSignal() {
		return EventSignal;
	}

	public void setEventSignal(float eventSignal) {
		EventSignal = eventSignal;
	}

	
	
	

}
