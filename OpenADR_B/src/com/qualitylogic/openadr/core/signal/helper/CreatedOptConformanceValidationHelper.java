package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CreatedOptConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCreatedOptType oadrCreatedOptType) {
		return OadrUtil.isSuccessResponse(oadrCreatedOptType.getEiResponse());
	}

	public static void validateRequestIDsMatch(OadrCreateOptType oadrCreateOpt,OadrCreatedOptType oadrCreatedOpt){
		
		if(TestSession.isValidationErrorFound()) return;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				String requestIDReceived = oadrCreateOpt.getRequestID();
				EiResponseType  eiResponseType  = oadrCreatedOpt.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCreateOpt and OadrCreatedOpt did not match");
				}
			} else {
				ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateOpt and OadrCreatedOpt match");
			}
			
			
	}
}
