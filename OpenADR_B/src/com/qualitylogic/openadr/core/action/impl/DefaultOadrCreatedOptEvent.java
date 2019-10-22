package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseCreatedOptEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;

public class DefaultOadrCreatedOptEvent extends BaseCreatedOptEventAction{


	private boolean isEventCompleted;


	// Default Constructor sets response code to 200.
	public DefaultOadrCreatedOptEvent(){
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

	
	public OadrCreatedOptType getCreatedOptTypResponse(){
		
		if(oadrCreatedOptType!=null) return oadrCreatedOptType;
		OadrCreateOptType oadrCreateOptType = TestSession.getCreateOptEventReceivedList().get(TestSession.getCreateOptEventReceivedList().size()-1);
		try {
			oadrCreatedOptType = CreatedOptEventHelper.loadOadrCreatedEvent("oadrCreatedOpt.xml");

			oadrCreatedOptType.getEiResponse().setResponseCode("200");
			oadrCreatedOptType.setOptID(oadrCreateOptType.getOptID());
			oadrCreatedOptType.getEiResponse().setRequestID(oadrCreateOptType.getRequestID());
			OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
			oadrPayload.getOadrSignedObject().setOadrCreatedOpt(oadrCreatedOptType);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreatedOptType; 
	}

}