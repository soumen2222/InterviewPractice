package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.BaseCreatedPartyRegistrationEventAction;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrProfiles.OadrProfile;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.signal.OadrTransports.OadrTransport;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class DefaultCreatedPartyRegistration extends BaseCreatedPartyRegistrationEventAction{

	private boolean isEventCompleted;

	private boolean isRequiredOnly;
	
	private boolean expectPreallocatedVenID;

	private boolean newRegistration;

	private boolean reRegistration;

	PropertiesFileReader propertiesFileReader=new PropertiesFileReader();
	Class requestType;

	public DefaultCreatedPartyRegistration(Class requestType){
		this.requestType=requestType;
	}

	public boolean isPreConditionsMet() {
		return true;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public void setRequiredOnly(boolean isRequiredOnly) {
		this.isRequiredOnly = isRequiredOnly;
	}
	
	public OadrCreatedPartyRegistrationType getOadrCreatedPartyRegistration() {
		if(oadrCreatedPartyRegistrationType != null) return oadrCreatedPartyRegistrationType;
		
		ArrayList<OadrCreatePartyRegistrationType>  createPartyRegistrationList = TestSession.getCreatePartyRegistrationTypeReceivedList();
		ArrayList<OadrQueryRegistrationType>  oadrQueryRegistrationList = TestSession.getOadrQueryRegistrationTypeReceivedList();
		String requestID="";
	
		
		try {
		
			oadrCreatedPartyRegistrationType = CreatedOptEventHelper.loadOadrCreatedPartyRegistration("oadrCreatedPartyRegistration.xml");

		
		if(requestType!=null && requestType.equals(OadrCreatePartyRegistrationType.class) && createPartyRegistrationList.size()>0){
			
			OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType = createPartyRegistrationList.get(createPartyRegistrationList.size()-1);
			requestID = oadrCreatePartyRegistrationType.getRequestID();
			
			if (expectPreallocatedVenID && StringUtil.isBlank(oadrCreatePartyRegistrationType.getVenID())) {
				throw new ValidationException("Expecting a preallocated venD");
			}

			if (newRegistration) {
				if (!StringUtil.isBlank(oadrCreatePartyRegistrationType.getRegistrationID())) {
					throw new ValidationException("This test case expects RegistrationID not to be included in the payload.");
				}

				String registrationID = OadrUtil.createUniqueRegistrationID();
				String venID = (propertiesFileReader.isUseStaticVENID()) ? propertiesFileReader.getVenID() : OadrUtil.createUniqueVENID();
				oadrCreatedPartyRegistrationType.setRegistrationID(registrationID);
				oadrCreatedPartyRegistrationType.setVenID(venID);
				
				new XMLDBUtil().setRegistrationID(registrationID);
				if (!new PropertiesFileReader().isUseStaticVENID()) {
					new XMLDBUtil().setVENID(venID);
				}
				
				// System.out.println("newRegistration registrationID=" + registrationID + " venID=" + venID);
			} else if (reRegistration) {
				String venID = oadrCreatePartyRegistrationType.getVenID();
				String registrationID = oadrCreatePartyRegistrationType.getRegistrationID(); 
				oadrCreatedPartyRegistrationType.setRegistrationID(registrationID);
				oadrCreatedPartyRegistrationType.setVenID(venID);
				
				if (StringUtil.isBlank(venID)) {
					throw new ValidationException("venID not in payload. VEN should be in registered state.");
				} else if (StringUtil.isBlank(registrationID)) {
					throw new ValidationException("registrationID not in payload. VEN should be in registered state.");
				}

				new XMLDBUtil().setRegistrationID(registrationID);
				if (!new PropertiesFileReader().isUseStaticVENID()) {
					new XMLDBUtil().setVENID(venID);
				}
			} else {
				String regID = new XMLDBUtil().getRegistrationID();
				oadrCreatedPartyRegistrationType.setRegistrationID(regID);
				
				String venID = new PropertiesFileReader().getVenID();
				oadrCreatedPartyRegistrationType.setVenID(venID);
			}
			
		}else if(requestType!=null && requestType.equals(OadrQueryRegistrationType.class) && oadrQueryRegistrationList.size()>0){
			OadrQueryRegistrationType oadrQueryRegistration = oadrQueryRegistrationList.get(oadrQueryRegistrationList.size()-1);
			requestID = oadrQueryRegistration.getRequestID();
			String regID = new XMLDBUtil().getRegistrationID();
			//String venID = new XMLDBUtil().getVENID();
			String venID = new PropertiesFileReader().getVenID();
			if(regID==null || regID.trim().equals("")){
				oadrCreatedPartyRegistrationType.setRegistrationID(null);
				oadrCreatedPartyRegistrationType.setVenID(null);
			}else{
				oadrCreatedPartyRegistrationType.setRegistrationID(regID);
				oadrCreatedPartyRegistrationType.setVenID(venID);
			}	
		}
		
			oadrCreatedPartyRegistrationType.getEiResponse().setRequestID(requestID);
			
			List<OadrProfile> profiles = oadrCreatedPartyRegistrationType.getOadrProfiles().getOadrProfile();
			OadrProfile eachProfile = Clone.clone(profiles.get(0));
			OadrTransport eachTransport=Clone.clone(eachProfile.getOadrTransports().getOadrTransport().get(0));

			eachProfile.getOadrTransports().getOadrTransport().clear();
			profiles.clear();
			
			String profileA=propertiesFileReader.getProfile_A();
			String profileATransport=propertiesFileReader.getProfile_A_Transport();
			
			String profileB=propertiesFileReader.getProfile_B();
			String profileBTransport=propertiesFileReader.getProfile_B_Transport();
			
			if(profileA!=null){
				OadrProfile profileAToAdd = Clone.clone(eachProfile);
				profiles.add(profileAToAdd);
				profileAToAdd.setOadrProfileName(profileA);
			
				String []profileATransportList = profileATransport.split(",");
				
				for(String eachProfileName:profileATransportList){	
					OadrTransport eachProfileATransport=Clone.clone(eachTransport);
					
					profileAToAdd.getOadrTransports().getOadrTransport().add(eachProfileATransport);
					
					if(eachProfileName.equalsIgnoreCase("SIMPLE_HTTP")){
						eachProfileATransport.setOadrTransportName(OadrTransportType.SIMPLE_HTTP);
					}else if(eachProfileName.equalsIgnoreCase("XMPP")){
						eachProfileATransport.setOadrTransportName(OadrTransportType.XMPP);
					}else{
						System.out.println("Wrong Profile Transport "+eachProfileName+" configured");
						System.exit(0);
					}
				}

				
				
				if(profileB!=null){
				
					OadrProfile profileBToAdd = Clone.clone(eachProfile);
					profiles.add(profileBToAdd);

					profileBToAdd.setOadrProfileName(profileB);
				
					String []profileBTransportList = profileBTransport.split(",");


					for(String eachProfileName:profileBTransportList){	
						OadrTransport eachProfileBTransport=Clone.clone(eachTransport);
						
						profileBToAdd.getOadrTransports().getOadrTransport().add(eachProfileBTransport);

						if(eachProfileName.equalsIgnoreCase("SIMPLE_HTTP")){
							eachProfileBTransport.setOadrTransportName(OadrTransportType.SIMPLE_HTTP);
						}else if(eachProfileName.equalsIgnoreCase("XMPP")){
							eachProfileBTransport.setOadrTransportName(OadrTransportType.XMPP);
						}else{
							System.out.println("Wrong Profile Transport "+eachProfileName+" configured");
							System.exit(0);
						}
					}
					
					
			}
		 }
			
			DurationPropType  durationPropType  = oadrCreatedPartyRegistrationType.getOadrRequestedOadrPollFreq();
			
			//Set this something lile PT5M. Also get this from the property file like second and stuff the value with S.
			//Enoch
			//durationPropType.setDuration(OadrUtil.createDuration(0, 20).toString());
			durationPropType.setDuration(propertiesFileReader.getCreatedPartyRegistration_RequestedOadrPollFreq());
			oadrCreatedPartyRegistrationType.setVtnID(propertiesFileReader.getVtnID());
			oadrCreatedPartyRegistrationType.getEiResponse().setResponseCode("200");
			oadrCreatedPartyRegistrationType.getEiResponse().setResponseDescription("OK");
			
			if (isRequiredOnly) {
				// remove optional elements
				oadrCreatedPartyRegistrationType.getEiResponse().setResponseDescription(null);
				oadrCreatedPartyRegistrationType.setOadrExtensions(null);
				oadrCreatedPartyRegistrationType.setOadrServiceSpecificInfo(null);
				if (TestSession.getMode().equals(ModeType.PUSH)) {
					oadrCreatedPartyRegistrationType.setOadrRequestedOadrPollFreq(null);
				}
			}
			
		} catch (ValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedPartyRegistrationType;
	}

	@Override
	public void setExpectPreallocatedVenID(boolean expected) {
		expectPreallocatedVenID = expected;
	}

	@Override
	public void setNewRegistration(boolean newRegistration) {
		this.newRegistration = newRegistration;
	}

	@Override
	public void setReRegistration(boolean reRegistration) {
		this.reRegistration = reRegistration;
	}
}
