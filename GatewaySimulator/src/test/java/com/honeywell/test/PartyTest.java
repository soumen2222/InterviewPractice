package com.honeywell.test;

import com.honeywell.pull.ven.VenSimServiceClient;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.services.VenSimSoapService;

public class PartyTest {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub

		VenSimServiceClient sim = new VenSimServiceClient();
		
		CreatePartyRegistrationRequest createPartyRegistrationRequest = new CreatePartyRegistrationRequest();
		
		CreatePartyRegistration createPartyRegistration = new CreatePartyRegistration() ;
		createPartyRegistration.setHttpPullMode(true);
		createPartyRegistration.setVenName("So12umen_TestVen7");
		createPartyRegistration.setXmlSignature(false);
		createPartyRegistration.setReportOnly(false);
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);		
		String baseurl = "http://127.0.0.1:8080/bvtn";  /// https://demo2vtn.openadr.com/bvtn/OpenADR2/Simple/2.0b/   "http://127.0.0.1:8080/bvtn";
		pushProfile.setPushUrl(baseurl);
		
		
		createPartyRegistrationRequest.setCreatePartyRegistration(createPartyRegistration );	
		createPartyRegistrationRequest.setPushProfile(pushProfile);
	
		
		try {
			sim.getVenSimService().registerGateway(createPartyRegistrationRequest);
			
		
		} catch (VenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
