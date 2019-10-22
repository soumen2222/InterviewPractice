package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CanceledOptConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCanceledOptType oadrCanceledOptType){
		return OadrUtil.isSuccessResponse(oadrCanceledOptType.getEiResponse());
	}

/*	public static boolean isVenIDValid(OadrCanceledOptType oadrCanceledOptType) {
		
		oadrCanceledOptType.getEiResponse().g
		
		if(!OadrUtil.isVENIDValid(oadrCancelOptType.getVenID())){
			return false;
		}

		return true;
	}
	*/
}
