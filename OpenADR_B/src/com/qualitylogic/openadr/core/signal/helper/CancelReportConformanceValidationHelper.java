package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CancelReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCanceledReportType oadrCanceledReportType) {
		return OadrUtil.isSuccessResponse(oadrCanceledReportType.getEiResponse());
	}

	public static boolean isVenIDValid(OadrCancelReportType oadrCancelReportType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCancelReportType.getVenID(),direction)){
			return false;
		}

		return true;
}
}
