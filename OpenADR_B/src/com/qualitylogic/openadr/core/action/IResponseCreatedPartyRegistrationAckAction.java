package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;

public interface IResponseCreatedPartyRegistrationAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCreatedPartyRegistrationType getOadrCreatedPartyRegistration();

	public boolean isLastEvent();
	public void setlastEvent(boolean isLastCreatedPartyRegistration);
	public void resetToInitialState();
	
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
	
	void setExpectPreallocatedVenID(boolean expected);
	void setNewRegistration(boolean newRegistration);
	void setReRegistration(boolean reRegistration);
}
