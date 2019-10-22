package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CanceledReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCanceledReportType oadrCanceledReportType) {
		return OadrUtil.isSuccessResponse(oadrCanceledReportType.getEiResponse());
	}

	public static boolean isVenIDValid(OadrCanceledReportType oadrCanceledReportType,Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCanceledReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isRequestIDsMatch(OadrCanceledReportType oadrCanceledReportType,Direction direction){
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrCancelReportType>  oadrCancelReportTypeList= null;
			if(direction.equals(Direction.Receive)){
				oadrCancelReportTypeList= TestSession.getOadrCancelReportTypeSentList();
			}else{
				oadrCancelReportTypeList= TestSession.getOadrCancelReportTypeReceivedList();			
			}
			
			if(oadrCancelReportTypeList==null||oadrCancelReportTypeList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any OadrCancelReport to map with OadrCanceledReport");
				return false;
			}
			
			OadrCancelReportType oadrCancelReportType = oadrCancelReportTypeList.get(oadrCancelReportTypeList.size()-1);
			if(!validateRequestIDsMatch(oadrCancelReportType,oadrCanceledReportType)){
				return false;
			}
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrRegisterReport and OadrRegisteredReport");
		}
		
		return true;
	}
	
	public static boolean validateRequestIDsMatch(OadrCancelReportType oadrCancelReportType,OadrCanceledReportType oadrCanceledReportType){
		
		if(TestSession.isValidationErrorFound()) return true;
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

			String requestIDReceived = oadrCancelReportType.getRequestID();
			EiResponseType  eiResponseType  = oadrCanceledReportType.getEiResponse();
			
			if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
				ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCancelReport and OadrCanceledReport did not match");
				return false;
			}
		} else {
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCancelReport and OadrCanceledReport match");
		}
		
		return true;
	}

}
