package com.honeywell.ven.api.registration;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;

@XmlRootElement
public class CancelPartyRegistration extends BaseClass {
	
	private String registrationId;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}
