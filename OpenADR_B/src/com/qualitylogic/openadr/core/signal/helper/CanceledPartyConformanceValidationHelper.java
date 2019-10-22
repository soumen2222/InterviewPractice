package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CanceledPartyConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType) {
		return OadrUtil.isSuccessResponse(oadrCanceledPartyRegistrationType.getEiResponse());
	}

	public static boolean isRequestIDsMatch(OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType,Direction direction){
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrCancelPartyRegistrationType>  canceleReportList= null;
			if(direction.equals(Direction.Receive)){
				canceleReportList= TestSession.getCancelPartyRegistrationTypeSentList();
			}else{
				canceleReportList= TestSession.getCancelPartyRegistrationTypeListReceived();
			}
			
			if(canceleReportList==null||canceleReportList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any OadrCancelPartyRegistration to map with OadrCanceledPartyRegistration");
				return false;
			}
			
			OadrCancelPartyRegistrationType oadrRegisterReportType = canceleReportList.get(canceleReportList.size()-1);
			if(!validateRequestIDsMatch(oadrRegisterReportType,oadrCanceledPartyRegistrationType)){
				return false;
			}
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrRegisterReport and OadrRegisteredReport");
		}
		
		return true;
	}
	
	public static boolean validateRequestIDsMatch(OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType,OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType){
		
		if(TestSession.isValidationErrorFound()) return true;
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

			String requestIDReceived = oadrCancelPartyRegistrationType.getRequestID();
			EiResponseType  eiResponseType  = oadrCanceledPartyRegistrationType.getEiResponse();
			
			if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
				ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCancelPartyRegistration and OadrCanceledPartyRegistration did not match");
				return false;
				
			}
		} else {
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCancelPartyRegistration and OadrCanceledPartyRegistration match");
		}
		return true;
	}
	
	public static boolean isVenIDValid(OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType,Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCanceledPartyRegistrationType.getVenID(),direction)){
			return false;
		}

		return true;
}
}
