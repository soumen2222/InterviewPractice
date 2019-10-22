package com.qualitylogic.openadr.core.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;

// TODO move this code to VENHandler
public class VENServerResource {
	static ArrayList<OadrDistributeEventType> oadrDistributeEventReceivedsList = new ArrayList<OadrDistributeEventType>();
	
	public static ArrayList<OadrDistributeEventType> getOadrDistributeEventReceivedsList() {
		return oadrDistributeEventReceivedsList;
	}
}
