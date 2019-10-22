package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;

public abstract class BaseRegisterReportAction implements IResponseRegisterReportTypeAckAction {
	protected boolean isEventCompleted;
	protected OadrRegisterReportType registerReportResponse;
	
	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public boolean isIslastEvent() {
		return islastEvent;
	}

	public void setIslastEvent(boolean islastEvent) {
		this.islastEvent = islastEvent;
	}

	protected OadrRegisterReportType oadrRegisterReportType = null;
	boolean islastEvent;
	boolean isEventPickedUpFromOadrPoll;
	IPrompt prompt;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		registerReportResponse = null;
	}
	
	public boolean islastEvent(){
		return islastEvent;
	}
	public void setlastEvent(boolean islastEvent){
		this.islastEvent=islastEvent;
	}

	@Override	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	@Override
	public IPrompt getPrompt(){
		return prompt;
	}
}