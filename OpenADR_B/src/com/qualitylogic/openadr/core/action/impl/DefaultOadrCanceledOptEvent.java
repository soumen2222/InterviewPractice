package com.qualitylogic.openadr.core.action.impl;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.BaseCanceledOptEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.helper.CanceledOptEventHelper;

public class DefaultOadrCanceledOptEvent extends BaseCanceledOptEventAction{


	private boolean isEventCompleted;
	private String responseCode = "200";

	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultOadrCanceledOptEvent(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultOadrCanceledOptEvent(){

	}

	public boolean isPreConditionsMet() {
		return true;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrCanceledOptType getOadrCanceledOptType() {
		ArrayList<OadrCancelOptType>  oadrCancelOptTypeList = TestSession.getCancelOptTypeReceivedList();
		String reqID = "Req1234";
		String optID = "OptID";
		
		if(oadrCancelOptTypeList.size()>0){
			OadrCancelOptType oadrCancelOptType=oadrCancelOptTypeList.get(oadrCancelOptTypeList.size()-1);
			reqID = oadrCancelOptType.getRequestID();
			optID = oadrCancelOptType.getOptID();
			
		}
		try {
			oadrCanceledOptType = CanceledOptEventHelper.loadOadrCanceledEvent("oadrCanceledOpt.xml");
			oadrCanceledOptType.getEiResponse().setResponseCode(this.responseCode);
			oadrCanceledOptType.getEiResponse().setRequestID(reqID);
			oadrCanceledOptType.setOptID(optID);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCanceledOptType;
	}

}