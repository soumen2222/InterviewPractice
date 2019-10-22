package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;

	public interface IResponseCanceledPartyRegistrationAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCanceledPartyRegistrationType getOadrCanceledPartyRegistration();

	public boolean islastEvent();
	public void setlastEvent(boolean isLastCanceledPartyRegistration);
	public void resetToInitialState();
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
}
