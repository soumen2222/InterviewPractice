package com.honeywell.ven.services.messages;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RegisterMultipleGatewayResponse {
	
		
	List<String> RegisteredGateway;

	public List<String> getRegisteredGateway() {
		return RegisteredGateway;
	}

	public void setRegisteredGateway(List<String> registeredGateway) {
		RegisteredGateway = registeredGateway;
	}



	

}
