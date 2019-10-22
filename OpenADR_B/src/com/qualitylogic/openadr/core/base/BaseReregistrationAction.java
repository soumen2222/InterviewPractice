package com.qualitylogic.openadr.core.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestReregistrationSignalHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.CreatedEventTimeoutThread;

public abstract class BaseReregistrationAction implements
		IRequestReregistrationAction{
	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	boolean isLastOadrRequestReregistration;

	protected OadrRequestReregistrationType oadrRequestReregistrationType = null;
	private ICreatedEventResult createdEventTimeoutAction;

	private boolean isEventPickedUpFromOadrPoll;
	List<OadrCreateOptType> resourceResultList = null;
	
	public boolean isPreConditionsMet() {

		if (oadrRequestReregistrationType == null) {
			oadrRequestReregistrationType = getOadrRequestReregistration();
		}
			//resetToInitialState();
		
	 return true;
	}


	public void resetToInitialState() {
		isEventCompleted = false;
		oadrRequestReregistrationType = null;

	}
	
	public boolean islastOadrRequestReregistration(){
		return isLastOadrRequestReregistration;
	}
	
	public void setlastOadrRequestReregistration(boolean isLastOadrRequestReregistration){
		this.isLastOadrRequestReregistration=isLastOadrRequestReregistration;
	}
	
	public void setEventPickedUpFromOadrPoll(boolean isEventPickedUpFromOadrPoll) {
		this.isEventPickedUpFromOadrPoll = isEventPickedUpFromOadrPoll;
	}

	public boolean isEventPickedUpFromOadrPoll(){
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