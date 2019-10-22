package com.honeywell.ven.entities.event;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.honeywell.ven.api.fault.VenEventNoResultException;


public interface VenEventEAO {
	
	
	@Remote
	public interface R extends VenEventEAO {}
	
	@Local
    public interface L extends VenEventEAO {}
	
	public void createEventdetail(VenEvent Eventdetail) throws ConstraintViolationException;
	
	public void deleteEventdetail(VenEvent Eventdetail);
	
	public void updateEventdetail(VenEvent Eventdetail);
	
	public List<VenEvent> getallEventDetails();
			
	public VenEvent getEventDetailsbyvenid(String VenID) throws VenEventNoResultException;
	
	
}
