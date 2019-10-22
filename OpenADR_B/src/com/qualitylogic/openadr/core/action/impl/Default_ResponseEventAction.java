package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class Default_ResponseEventAction implements IResponseEventAction {

	private boolean isEventCompleted;
	private String responseCode = "200";
	private boolean isLastEvent;
	// Use this Constructor if you want to set specific response code other than
	// 200.
	public Default_ResponseEventAction(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public Default_ResponseEventAction() {
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

	@Override
	public OadrResponseType getResponse() {

		OadrResponseType oadrResponseType = null;
		try {
			oadrResponseType = ResponseHelper
					.loadOadrResponse("oadrResponse_successdefault.xml");
			oadrResponseType.getEiResponse().setResponseCode(responseCode);
			if (!responseCode.equals(ErrorConst.OK_200)) {
				oadrResponseType.getEiResponse().setResponseDescription("ERROR");
			}
			oadrResponseType.setVenID(new PropertiesFileReader().getVenID());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrResponseType;
	}


	public boolean islastEvent(){
		return isLastEvent;
	}
	
	public void setlastEvent(boolean isLastEvent){
		this.isLastEvent=isLastEvent;
	}
	
	@Override
	public void resetToInitialState() {
		isEventCompleted=false;		
	}
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}