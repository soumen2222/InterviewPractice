package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.action.ICreateOptEventAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;

public abstract class BaseCreateOptEventAction implements ICreateOptEventAction {
	protected boolean isEventCompleted;
	protected OadrCreatedOptType oadrCreateOptType;
	boolean lastEvent;

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreateOptType = null;
	}
	
	public boolean islastEvent(){
		return lastEvent;
	}
	public void setlastEvent(boolean lastEvent){
		this.lastEvent=lastEvent;
	}
	
}