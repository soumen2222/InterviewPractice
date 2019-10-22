package com.qualitylogic.openadr.core.action;

import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;

	public interface IResponseRegisteredReportTypeAckAction {
	
	public abstract boolean isPreConditionsMet();

	public abstract boolean isEventCompleted();

	public void setEventCompleted(boolean isEventCompleted);

	public OadrRegisteredReportType getOadrRegisteredReportResponse();
	public void resetToInitialState();
	
	public boolean islastEvent();
	public void setlastEvent(boolean islastEvent);

	public void setEventPickedUpFromOadrPoll(boolean isEventPickedUpFromOadrPoll);
	public boolean isEventPickedUpFromOadrPoll();
	
	public void setPrompt(IPrompt prompt);
	public IPrompt getPrompt();
}
