package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrTransportType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class CreatePartyRegistrationConforValHelper {

	// Additional Checks
	public static boolean isRegIDAndVENIDMatch(String data) {
		
		OadrCreatePartyRegistrationType  oadrCreatePartyRegistration = CreatePartyRegistrationReqSignalHelper.createCreatePartyRegistrationRequestFromString(data);
		
		if (oadrCreatePartyRegistration != null){
			XMLDBUtil xmlDB = new XMLDBUtil();
			String registrationIDInDB = xmlDB.getRegistrationID();
			//String venIDInDB = xmlDB.getVENID();
			String venIDInDB = new PropertiesFileReader().getVenID();
			if(oadrCreatePartyRegistration.getRegistrationID()==null){
				return true;
			}else if(registrationIDInDB==null || registrationIDInDB.length()<1){
				return true;
			}else if((registrationIDInDB.equals(oadrCreatePartyRegistration.getRegistrationID()) && venIDInDB.equals(oadrCreatePartyRegistration.getVenID()))){
				return true;
			}else{
				return false;
			}

			
		}
		return true;
	}


	public static boolean isTransportAddressPresent(String data) {
		OadrCreatePartyRegistrationType  oadrCreatePartyRegistration = CreatePartyRegistrationReqSignalHelper.createCreatePartyRegistrationRequestFromString(data);

		if(!OadrUtil.isCreatePartyRegistration_PullModel()){
			String transportAddress = oadrCreatePartyRegistration.getOadrTransportAddress();
			if(transportAddress==null || transportAddress.length()<1){
				return false;				
			}else{
				return true;
			}
		}
		return true;
	}
	
			
	
	public static boolean isCreatePartyRegistration_HttpPullModelTrueFalse_Check(String data) {
		OadrCreatePartyRegistrationType  oadrCreatePartyRegistration = CreatePartyRegistrationReqSignalHelper.createCreatePartyRegistrationRequestFromString(data);
		
		OadrTransportType  oadrTransportType  = oadrCreatePartyRegistration.getOadrTransportName();
		
		if(oadrTransportType==null) return true;
		
		if(oadrTransportType.equals(OadrTransportType.SIMPLE_HTTP)){
			
			if(oadrCreatePartyRegistration.isOadrHttpPullModel()==null){
				LogHelper.addTrace("Conformance check failed : oadrHttpPullModel is not specified. If the oadrTransportName is simpleHttp, then oadrHttpPullModel MUST be set to true or false");
			return false;
			}
			
			if(oadrCreatePartyRegistration.isOadrHttpPullModel()==false){
				
				String transportAddress = oadrCreatePartyRegistration.getOadrTransportAddress();
				if(transportAddress==null || transportAddress.length()<1){
					LogHelper.addTrace("Conformance check failed : The oadrTransportAddress was not included for push exchange");				
					return false;				
				}
			}
			
		}
		
		
		if(!OadrUtil.isCreatePartyRegistration_PullModel()){
			String transportAddress = oadrCreatePartyRegistration.getOadrTransportAddress();
			if(transportAddress==null || transportAddress.length()<1){
				return false;				
			}else{
				return true;
			}
		}
		return true;
	}
	
	public static boolean isVenIDValid(OadrCreatePartyRegistrationType oadrCreatePartyRegistration, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCreatePartyRegistration.getVenID(),direction)){
			return false;
		}

		return true;
}
}
