package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;

public abstract class BaseCancelPartyRegistrationAction implements IResponseCancelPartyRegistrationAckAction {
	protected boolean isEventCompleted;
	protected OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType;
	boolean lastCancelPartyRegistration;
	boolean isEventPickedUpFromOadrPoll;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCancelPartyRegistrationType = null;
	}
	
	public synchronized boolean isExpectedCreateEventReceived() {

		return false;
	}
	
	public boolean islastCancelPartyRegistration(){
		return lastCancelPartyRegistration;
	}
	public void setlastCancelPartyRegistration(boolean lastCanceledPartyRegistration){
		this.lastCancelPartyRegistration=lastCanceledPartyRegistration;
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}