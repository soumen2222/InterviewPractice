package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;

	public interface IRequestReregistrationAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrRequestReregistrationType getOadrRequestReregistration();
	
	public void resetToInitialState();
	
	public boolean islastOadrRequestReregistration();
	
	public void setlastOadrRequestReregistration(boolean isLastOadrRequestReregistration);

	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
	
}
