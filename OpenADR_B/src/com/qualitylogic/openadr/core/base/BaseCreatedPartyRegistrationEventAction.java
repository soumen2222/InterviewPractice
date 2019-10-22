package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;

public abstract class BaseCreatedPartyRegistrationEventAction implements IResponseCreatedPartyRegistrationAckAction {
	protected boolean isEventCompleted;
	protected OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType;
	boolean lastCreatedPartyRegistration;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreatedPartyRegistrationType = null;
	}
	
	public boolean isLastEvent(){
		return lastCreatedPartyRegistration;
	}
	public void setlastEvent(boolean lastCreatedPartyRegistration){
		this.lastCreatedPartyRegistration=lastCreatedPartyRegistration;
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}