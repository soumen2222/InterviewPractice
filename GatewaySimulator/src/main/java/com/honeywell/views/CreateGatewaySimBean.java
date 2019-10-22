package com.honeywell.views;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.ws.BindingProvider;
import com.sun.xml.ws.client.BindingProviderProperties;
import com.honeywell.pull.ven.VenSimServiceClient;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.services.VenSimService;
import com.honeywell.ven.services.messages.RegisterGatewayResponse;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayRequest;
import com.honeywell.ven.services.messages.RegisterMultipleGatewayResponse;

@ViewScoped
@ManagedBean(name = "createGatewaySimBean")
public class CreateGatewaySimBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String DRASVTNUrl;
	private String DRASServerUrl;
	private String VenName;
	private VenSimService venSimService=null;
	private String message ="";	
	private int numofVens;
	
	
	public String getDRASVTNUrl() {
		return DRASVTNUrl;
	}
	public void setDRASVTNUrl(String dRASVTNUrl) {
		DRASVTNUrl = dRASVTNUrl;
	}
	public String getDRASServerUrl() {
		return DRASServerUrl;
	}
	public void setDRASServerUrl(String dRASServerUrl) {
		DRASServerUrl = dRASServerUrl;
	}
	public String getVenName() {
		return VenName;
	}
	public void setVenName(String venName) {
		VenName = venName;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
		
	public int getNumofVens() {
		return numofVens;
	}
	public void setNumofVens(int numofVens) {
		this.numofVens = numofVens;
	}
	
	
	public void createVen() throws VenException{
	this.message ="";		
     CreatePartyRegistrationRequest createPartyRegistrationRequest = new CreatePartyRegistrationRequest();		
	CreatePartyRegistration createPartyRegistration = new CreatePartyRegistration() ;
	createPartyRegistration.setHttpPullMode(true);
	createPartyRegistration.setVenName(VenName);
	createPartyRegistration.setXmlSignature(false);
	createPartyRegistration.setReportOnly(false);
	PushProfile pushProfile = new PushProfile() ;
	ProfileName profileName = null ;
	pushProfile.setProfileName(profileName.PROFILE2B);
	TransportName transport = null;
	pushProfile.setTransport(transport.simpleHttp);		
	String baseurl = DRASVTNUrl;  
	pushProfile.setPushUrl(baseurl);
	pushProfile.setServerUrl(DRASServerUrl);
	createPartyRegistrationRequest.setCreatePartyRegistration(createPartyRegistration );	
	createPartyRegistrationRequest.setPushProfile(pushProfile);
    
	RegisterGatewayResponse response = null;
	try {
				response = getVenSimService().registerGateway(createPartyRegistrationRequest);
		
	
	} catch (VenException e) {
		// TODO Auto-generated catch block
		throw new VenException("Error in Customer end point creation ",e);
	}
	if (response!=null){
		this.message ="Gateway Created Successfully";
	}else
	{
		this.message ="Gateway Creation Failed check the logs";
	}
		
}
	
	public void createMultipleVen() throws VenException{
		this.message ="";	
		RegisterMultipleGatewayRequest registerMultipleGatewayRequest = new RegisterMultipleGatewayRequest();
	     CreatePartyRegistrationRequest createPartyRegistrationRequest = new CreatePartyRegistrationRequest();		
		CreatePartyRegistration createPartyRegistration = new CreatePartyRegistration() ;
		createPartyRegistration.setHttpPullMode(true);
		createPartyRegistration.setVenName(VenName);
		createPartyRegistration.setXmlSignature(false);
		createPartyRegistration.setReportOnly(false);
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);		
		String baseurl = DRASVTNUrl;  
		pushProfile.setPushUrl(baseurl);
		pushProfile.setServerUrl(DRASServerUrl);
		createPartyRegistrationRequest.setCreatePartyRegistration(createPartyRegistration );	
		createPartyRegistrationRequest.setPushProfile(pushProfile);
		registerMultipleGatewayRequest.setCreatePartyRegistrationRequest(createPartyRegistrationRequest);
		registerMultipleGatewayRequest.setNumofgateways(numofVens);
		RegisterMultipleGatewayResponse response = null;
		try {
					
					response = getVenSimService().createMultipleVens(registerMultipleGatewayRequest);			
		
		} catch (VenException e) {
			// TODO Auto-generated catch block
			throw new VenException("Error in Customer end point creation ",e);
		}
		if (response!=null){
			this.message ="Gateway Created Successfully";
		}else
		{
			this.message ="Gateway Creation Failed check the logs";
		}
			
	}
	
	private VenSimService  getVenSimService() {
		if (venSimService == null) {
			venSimService = new VenSimServiceClient().getVenSimService();
			
			try {
				Map<String, Object> requestContext = ((BindingProvider)venSimService).getRequestContext();
				requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 900000); // Timeout in millis
				requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 300000); // Timeout in millis				
			} catch (Exception e) {
				Logger.getLogger(CreateGatewaySimBean.class.getName()).log(Level.SEVERE,
						"Exception in setting timeout", e.getMessage());
			}		
		}		
		return venSimService;

	}
	


}
