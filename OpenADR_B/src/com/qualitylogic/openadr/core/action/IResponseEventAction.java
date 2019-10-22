package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrResponseType;

public interface IResponseEventAction {

	public abstract boolean isPreConditionsMet();
	public abstract boolean isEventCompleted();
	public void setEventCompleted(boolean isEventCompleted);
	public OadrResponseType getResponse();
	public void resetToInitialState();
	public abstract boolean islastEvent();
	public void setlastEvent(boolean isLastEvent);
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
}