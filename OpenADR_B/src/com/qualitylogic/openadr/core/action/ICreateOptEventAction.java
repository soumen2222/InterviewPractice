package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public interface ICreateOptEventAction {

	OadrCreateOptType getOadrCreateOpt();
	public abstract boolean isPreConditionsMet();
	public boolean islastEvent();
	public void setlastEvent(boolean lastEvent);
	void resetToInitialState();	
}