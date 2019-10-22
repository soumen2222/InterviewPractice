package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;

	public interface IResponseCreatedOptAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCreatedOptType getCreatedOptTypResponse();
	
	public void resetToInitialState();
	
	public boolean islastEvent();
	public void setlastEvent(boolean lastEvent);

	public IResponseCreatedOptAckAction setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
	

}
