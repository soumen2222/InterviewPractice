package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;

public interface IResponseCancelReportTypeAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrCancelReportType getOadrCancelReportResponse();
	public void resetToInitialState();
	
	public boolean islastEvent();
	public void setlastEvent(boolean islastEvent);

	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();

	public void setReportToFollow(boolean reportToFollow);
}
