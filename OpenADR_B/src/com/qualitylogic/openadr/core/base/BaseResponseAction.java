package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;

public abstract class BaseResponseAction implements IResponseEventAction {
	protected boolean isEventCompleted;
	protected OadrResponseType oadrResponse;
	boolean lastCancelPartyRegistration;
	boolean isEventPickedUpFromOadrPoll;
	boolean isLastEvent;
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrResponse = null;
	}
	
	public boolean isEventCompleted(){
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted){
		this.isEventCompleted=isEventCompleted;
	}
	
	public boolean islastEvent(){
		return isLastEvent;
	}
	public void setlastEvent(boolean isLastEvent){
		this.isLastEvent=isLastEvent;
	}

	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}