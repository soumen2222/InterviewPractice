package com.honeywell.pull.ven;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.entities.event.VenEvent;
import com.honeywell.ven.entities.registration.Registration;
import com.honeywell.ven.entities.report.Reportdetail;
import com.honeywell.ven.services.messages.TotalVenDataResponse;



public interface VenManager  {
	@Remote
    public interface R extends VenManager {}
    @Local
    public interface L extends VenManager {}
	
    
    public void updateEventdetail(VenEvent Eventdetail);
    public VenEvent getEventDetailsbyvenid(String Venid);
    public List<VenEvent> getallEventDetails();
    
    public void createRegistration(Registration Registrationdetail);
    public List<Registration> getallRegistrationdetails();	
	public Registration getRegistrationdetailssbyvenid(String venid);
	public void updateRegistration(Registration Registrationdetail);
	
	public void createReportdetail(List<Reportdetail> reportdetails);
	
	public TotalVenDataResponse getallVendetails();
	
	
}
