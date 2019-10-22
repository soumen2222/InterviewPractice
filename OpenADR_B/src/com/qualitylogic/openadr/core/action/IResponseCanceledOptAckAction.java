package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;

	public interface IResponseCanceledOptAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCanceledOptType getOadrCanceledOptType();
	
	public boolean islastEvent();
	public void setlastEvent(boolean lastCanceledOpt);
	
}
