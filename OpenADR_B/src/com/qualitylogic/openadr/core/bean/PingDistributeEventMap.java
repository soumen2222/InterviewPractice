package com.qualitylogic.openadr.core.bean;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;

public class PingDistributeEventMap {
	private String distributeEventRequestID;
	private ArrayList<OadrDistributeEventType> oadrDistributeEventList = new ArrayList<OadrDistributeEventType>();

	public String getDistributeEventRequestID() {
		return distributeEventRequestID;
	}

	public void setDistributeEventRequestID(String distributeEventID) {
		this.distributeEventRequestID = distributeEventID;
	}

	public ArrayList<OadrDistributeEventType> getDistributeEventList() {
		return oadrDistributeEventList;
	}

	public void addDistributeEvent(OadrDistributeEventType distributeEvent) {
		oadrDistributeEventList.add(distributeEvent);
	}

}
