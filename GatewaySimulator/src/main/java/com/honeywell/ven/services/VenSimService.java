package com.honeywell.ven.services;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.registration.CancelPartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.services.messages.RegisterGatewayResponse;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayRequest;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayResponse;
import com.honeywell.ven.services.messages.TotalVenDataResponse;


@WebService(name = "VenSimService", targetNamespace = "http://services.ven.honeywell.com/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface VenSimService {

	@WebMethod(operationName = "registerGateway")
	public RegisterGatewayResponse registerGateway(CreatePartyRegistrationRequest createPartyRegistrationRequest) throws VenException;
	
	@WebMethod(operationName = "cancelPartyRegistration")
	public void cancelPartyRegistration(CancelPartyRegistration cancelPartyRegistrationRequest)
			throws VenException;
	
	public RegisterMultipleGatewayResponse createMultipleVens(RegisterMultipleGatewayRequest registerMultipleGatewayRequest)
			throws VenException;
		
	@WebMethod
	public TotalVenDataResponse getAllVendata() throws VenException;
}
