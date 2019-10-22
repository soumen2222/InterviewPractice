package com.honeywell.ven.services.messages;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;

@XmlRootElement
public class RegisterMultipleGatewayRequest {
	
		
	int numofgateways ;
	CreatePartyRegistrationRequest createPartyRegistrationRequest;


	public int getNumofgateways() {
		return numofgateways;
	}

	public void setNumofgateways(int numofgateways) {
		this.numofgateways = numofgateways;
	}

	public CreatePartyRegistrationRequest getCreatePartyRegistrationRequest() {
		return createPartyRegistrationRequest;
	}

	public void setCreatePartyRegistrationRequest(
			CreatePartyRegistrationRequest createPartyRegistrationRequest) {
		this.createPartyRegistrationRequest = createPartyRegistrationRequest;
	}

	

	

}
