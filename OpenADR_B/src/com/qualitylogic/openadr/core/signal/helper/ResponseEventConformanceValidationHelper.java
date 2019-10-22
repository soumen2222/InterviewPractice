package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class ResponseEventConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(OadrResponseType oadrResponse) {
		if (oadrResponse != null
				&& oadrResponse.getEiResponse() != null
				&& !oadrResponse.getEiResponse().getResponseCode()
						.startsWith("2")) {
			return false;
		}
		return true;
	}
	
	public static boolean isVenIDValid(OadrResponseType oadrResponse,Direction direction) {

		if(!OadrUtil.isVENIDValid(oadrResponse.getVenID(),direction)){
			return false;
		}
		
		return true;
	}
}
