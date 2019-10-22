package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;

public abstract class BaseCreatedOptEventAction implements IResponseCreatedOptAckAction{
	protected boolean isEventCompleted;
	protected OadrCreatedOptType oadrCreatedOptType;
	boolean lastEvent;
	IPrompt prompt;
	
	public boolean isPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreatedOptType = null;
	}
	
	public synchronized boolean isExpectedCreatedEventReceived() {

		return false;
	}
	
	public boolean islastEvent(){
		return lastEvent;
	}
	public void setlastEvent(boolean lastEvent){
		this.lastEvent=lastEvent;
	}

	public IResponseCreatedOptAckAction setPrompt(IPrompt prompt){
		this.prompt=prompt;
		return this;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}