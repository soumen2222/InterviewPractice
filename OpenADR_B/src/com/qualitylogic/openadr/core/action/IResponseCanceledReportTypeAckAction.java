package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;

public interface IResponseCanceledReportTypeAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCanceledReportType getOadrCanceledReportResponse();
	
	public void resetToInitialState();
	
	public boolean islastEvent();
	public void setlastEvent(boolean islastEvent);
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();

	public void addReportRequestID(String reportRequestID);
}
