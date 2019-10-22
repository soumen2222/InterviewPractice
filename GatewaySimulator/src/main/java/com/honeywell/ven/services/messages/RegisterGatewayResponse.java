package com.honeywell.ven.services.messages;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.entities.registration.Registration;

@XmlRootElement
public class RegisterGatewayResponse {
	
		
	Registration Registrationdetail ;

	public Registration getRegistrationdetail() {
		if (Registrationdetail == null) {
			Registrationdetail = new Registration();
		}
		
		
		return Registrationdetail;
	}

	public void setRegistrationdetail(Registration registrationdetail) {
		Registrationdetail = registrationdetail;
	}


	

}
