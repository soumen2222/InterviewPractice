package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

	public class RequestReRegistrationConforValHelper {
		
	public static boolean isVenIDValid(OadrRequestReregistrationType oadrRequestReregistrationType,Direction direction) {
	
			if(!OadrUtil.isVENIDValid(oadrRequestReregistrationType.getVenID(),direction)){
				return false;
			}
	
			return true;
	}
	}

