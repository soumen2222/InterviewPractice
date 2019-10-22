package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;

public abstract class BaseRegisteredReportAction implements IResponseRegisteredReportTypeAckAction {
	protected boolean isEventCompleted;
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

	protected OadrRegisteredReportType oadrRegisteredReportType = null;
	boolean islastEvent;
	boolean isEventPickedUpFromOadrPoll;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrRegisteredReportType = null;
	}
	
	public boolean islastEvent(){
		return islastEvent;
	}
	public void setlastEvent(boolean islastEvent){
		this.islastEvent=islastEvent;
	}
	
	@Override
	public void setEventPickedUpFromOadrPoll(boolean isEventPickedUpFromOadrPoll) {
		this.isEventPickedUpFromOadrPoll =isEventPickedUpFromOadrPoll;
	}

	@Override
	public boolean isEventPickedUpFromOadrPoll() {
		return isEventPickedUpFromOadrPoll;
	}

	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}