package com.honeywell.ven.entities.registration;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.ven.services.messages.TotalVenDataResponse;

public interface RegistrationEAO {
	
	
	@Remote
	public interface R extends RegistrationEAO {}
	
	@Local
    public interface L extends RegistrationEAO {}
	
	public void createRegistration(Registration Registrationdetail);
	
	public void deleteRegistration(Registration Registrationdetail);
	
	public void updateRegistration(Registration Registrationdetail);
	
	public List<Registration> getallRegistrationdetails();
			
	public Registration getRegistrationdetailssbyvenid(String venid);
	
	public TotalVenDataResponse getallVendetails();
	
	
}
