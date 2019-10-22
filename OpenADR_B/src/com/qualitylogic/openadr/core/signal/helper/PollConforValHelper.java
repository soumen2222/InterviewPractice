package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

	public class PollConforValHelper {
		
	public static boolean isVenIDValid(OadrPollType oadrPollType, Direction direction) {
	
			if(!OadrUtil.isVENIDValid(oadrPollType.getVenID(),direction)){
				return false;
			}

			return true;
	}
	
	
/*	public static void validateRequestIDsMatch(OadrPollType req,String response){
		
		if(TestSession.isValidationErrorFound()) return;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				String requestIDReceived = req.;
				EiResponseType  eiResponseType  = oadrCreatedReport.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCreateReport and OadrCreatedReport did not match");
				}
			} else {
				ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateReport and OadrCreatedReport match");
			}
			
	}*/


	}

