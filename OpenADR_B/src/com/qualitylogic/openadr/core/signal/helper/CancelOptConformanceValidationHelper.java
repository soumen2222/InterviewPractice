package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CancelOptConformanceValidationHelper {

	public static boolean isOptIDValid(
			OadrCancelOptType oadrCancelOptType) {
		ArrayList<OadrCreateOptType>  oadrCreateOptList = null;

		if(TestSession.getServiceType()==ServiceType.VEN){
			oadrCreateOptList = TestSession.getCreateOptEventSentList();
		}else{
			oadrCreateOptList = TestSession.getCreateOptEventReceivedList();
		}
		
		String cancelOptID = oadrCancelOptType.getOptID();
		if(OadrUtil.isEmpty(cancelOptID)){
			return false;
		}
		
		
		for(OadrCreateOptType eachOadrCreateOptType:oadrCreateOptList){
			String createOptID = eachOadrCreateOptType.getOptID();
			if(createOptID.trim().equals(cancelOptID.trim())){
				return true;
			}
		}
			LogHelper
			.addTrace("Conformance Validation Error : Unable to map CancelOpt "+cancelOptID+" to any OadrCreateOpt");
		
		return false;
	}

	public static boolean isVenIDValid(OadrCancelOptType oadrCancelOptType,Direction direction) {

		
		if(!OadrUtil.isVENIDValid(oadrCancelOptType.getVenID(),direction)){
			return false;
		}

		return true;
	}
}
