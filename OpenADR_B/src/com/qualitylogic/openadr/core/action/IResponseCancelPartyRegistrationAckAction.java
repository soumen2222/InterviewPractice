package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;

	public interface IResponseCancelPartyRegistrationAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCancelPartyRegistrationType getOadrCancelPartyRegistration();

	public boolean islastCancelPartyRegistration();
	public void setlastCancelPartyRegistration(boolean isLastCancelPartyRegistration);
	public void resetToInitialState();
	
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
	
}
