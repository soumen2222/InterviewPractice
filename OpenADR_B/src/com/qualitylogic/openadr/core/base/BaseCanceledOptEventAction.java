package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;

public abstract class BaseCanceledOptEventAction implements IResponseCanceledOptAckAction {
	protected boolean isEventCompleted;
	protected OadrCanceledOptType oadrCanceledOptType;
	boolean lastCanceledOpt;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCanceledOptType = null;
	}
	
	public synchronized boolean isExpectedCreatedEventReceived() {

		return false;
	}
	
	public boolean islastEvent(){
		return lastCanceledOpt;
	}
	public void setlastEvent(boolean lastCanceledOpt){
		this.lastCanceledOpt=lastCanceledOpt;
	}
	
}