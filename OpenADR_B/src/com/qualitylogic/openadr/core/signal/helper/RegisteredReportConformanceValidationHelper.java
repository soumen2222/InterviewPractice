package com.qualitylogic.openadr.core.signal.helper;
import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class RegisteredReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrRegisteredReportType oadrRegisteredReportType) {
		return OadrUtil.isSuccessResponse(oadrRegisteredReportType.getEiResponse());
	}

	public static boolean isVenIDValid(OadrRegisteredReportType oadrRegisteredReportType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrRegisteredReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isRequestIDsMatch(OadrRegisteredReportType oadrRegisteredReportType,Direction direction){
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrRegisterReportType>  registerReportList= null;
			if(direction.equals(Direction.Receive)){
				registerReportList= TestSession.getOadrRegisterReportTypeSentList();
			}else{
				registerReportList= TestSession.getOadrRegisterReportTypeReceivedList();			
			}
			
			if(registerReportList==null||registerReportList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any RegisterReport to map with RegisteredReport");
				return false;
			}
			
			OadrRegisterReportType oadrRegisterReportType = registerReportList.get(registerReportList.size()-1);
			if(!validateRequestIDsMatch(oadrRegisterReportType,oadrRegisteredReportType)){
				return false;
			}
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrRegisterReport and OadrRegisteredReport");
		}
		
		return true;
	}

	public static boolean validateRequestIDsMatch(OadrRegisterReportType oadrRegisterReportType,OadrRegisteredReportType oadrRegisteredReportType){
			
			if(TestSession.isValidationErrorFound()) return true;
			
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
	
				String requestIDReceived = oadrRegisterReportType.getRequestID();
				EiResponseType  eiResponseType  = oadrRegisteredReportType.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrRegisterReport and OadrRegisteredReport did not match");
					return false;
					
				}
			} else {
				ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrRegisterReport and OadrRegisteredReport match");
			}
			return true;
		}
}