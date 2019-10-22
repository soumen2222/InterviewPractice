package com.honeywell.ven.api.registration;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;


@XmlRootElement
public class CanceledPartyRegistration extends BaseClass{

	private Response response;
	private String registrationId;
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	
}
