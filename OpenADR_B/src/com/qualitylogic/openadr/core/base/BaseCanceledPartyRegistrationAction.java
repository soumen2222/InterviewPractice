package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;

public abstract class BaseCanceledPartyRegistrationAction implements IResponseCanceledPartyRegistrationAckAction {
	protected boolean isEventCompleted;
	protected OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType;
	boolean lastCanceledPartyRegistration;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCanceledPartyRegistrationType = null;
	}
	
	public synchronized boolean isExpectedCreatedEventReceived() {

		return false;
	}
	
	public boolean islastEvent(){
		return lastCanceledPartyRegistration;
	}
	public void setlastEvent(boolean lastCanceledPartyRegistration){
		this.lastCanceledPartyRegistration=lastCanceledPartyRegistration;
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}