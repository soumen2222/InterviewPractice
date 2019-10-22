package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class CancelPartyRegistrationConfValHelper {

	public static boolean isRegistrationValid(OadrCancelPartyRegistrationType oadrCancelPartyRegistration,Direction direction) {
		
		String dbRegistrationID = new XMLDBUtil().getRegistrationID();
		String dbVENID = new PropertiesFileReader().getVenID();
		
		if(dbRegistrationID==null)dbRegistrationID="";
		if(dbVENID==null)dbVENID="";
		
		ServiceType serviceType = TestSession.getServiceType();
		
		String cancelRegID = oadrCancelPartyRegistration.getRegistrationID();
		String cancelVENID = oadrCancelPartyRegistration.getVenID();
		
		if(cancelRegID==null || cancelRegID.length()<1) return false;
		
		if(((serviceType.equals(ServiceType.VTN) && direction.equals(Direction.Receive)) || (serviceType.equals(ServiceType.VEN) && direction.equals(Direction.Send)))){
			if(cancelVENID==null || cancelVENID.trim().length()<1){
				 LogHelper
					.addTrace("Conformance Validation Error : venID was expected as the payload was sent from VEN to the VTN.");
					
				 return false;
			}
			
		}
		
		
		if(cancelVENID!=null && cancelVENID.trim().length()>0){
			
			if(!dbVENID.trim().equals(cancelVENID)){
				 LogHelper
					.addTrace("Conformance Validation Error : Unknown venID has been received.");
				 return false;	
			}
			
			
		}

		if(dbRegistrationID.trim().equals(cancelRegID)){
			return true;	
		}else{
			return false;	
		}
		
		
	}
	
	
	public static boolean isVenIDValid(OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType,Direction direction) {
	
		if(!OadrUtil.isVENIDValid(oadrCancelPartyRegistrationType.getVenID(),direction)){
			return false;
		}

		return true;
}
	
}
