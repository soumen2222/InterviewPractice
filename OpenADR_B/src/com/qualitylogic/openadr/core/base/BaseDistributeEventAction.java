package com.qualitylogic.openadr.core.base;

import java.util.Date;
import java.util.List;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.CreatedEventTimeoutThread;

public abstract class BaseDistributeEventAction implements
		IDistributeEventAction {
	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	protected boolean isEventPickedUpFromOadrPoll;

	protected OadrDistributeEventType oadrDistributeEvent = null;
	private ICreatedEventResult createdEventTimeoutAction;

	List<OadrCreateOptType> resourceResultList = null;
	
	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}
	
	@Override
	public boolean isCommonPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		if(oadrRequestEvent==null) return true;
		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = getDistributeEvent();
		}
		if (DistributeEventSignalHelper
				.isReqReceivedMeetMinimumCumulativeNbrOfDistributeResponses(
						oadrDistributeEvent, oadrRequestEvent)) {
			return true;
		} else {
			resetToInitialState();
			Trace trace = TestSession.getTraceObj();

			if (trace != null) {
				trace.getLogFileContentTrace()
						.append("\nThe number of events to be dispatched in this OadrDistributeEventType exceeds the ReplyLimit specified by the OadrRequestEventType");
			}
			return false;
		}
	}

	
	public void setExpectedOadrCreateOptList(List<OadrCreateOptType> resourceResultList){
		this.resourceResultList=resourceResultList;
	}
	
	public List<OadrCreateOptType> getExpectedOadrCreateOptList(){
		
		return resourceResultList;
	}
	
	
	public void setEventPickedUpFromOadrPoll(boolean isEventPickedUpFromOadrPoll) {
		this.isEventPickedUpFromOadrPoll = isEventPickedUpFromOadrPoll;
	}

	public boolean isEventPickedUpFromOadrPoll(){
		
		return isEventPickedUpFromOadrPoll;
	
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		isEventPickedUpFromOadrPoll =  false;
		oadrDistributeEvent = null;

	}

	public void setCreatedEventSuccessCriteria(
			ICreatedEventResult createdEventTimeoutAction) {
		this.createdEventTimeoutAction = createdEventTimeoutAction;
	}

	public ICreatedEventResult getCreatedEventTimeoutAction() {
		return createdEventTimeoutAction;
	}

	public void startCreatedEventTimeoutActionThread() {
		ICreatedEventResult createdEventTimeoutAction = getCreatedEventTimeoutAction();
		if (createdEventTimeoutAction != null) {
			CreatedEventTimeoutThread createdEventTimeoutThread = new CreatedEventTimeoutThread(
					createdEventTimeoutAction);
			Thread createdEventThread = new Thread(createdEventTimeoutThread);
			createdEventThread.start();
		}
	}

	public void startCreatedEventTimeoutActionThreadWithPause() {
		startCreatedEventTimeoutActionThread();
		ICreatedEventResult createdEventResult = getCreatedEventTimeoutAction();

		while (!createdEventResult.isExpectedCreatedEventReceived()) {
			if (TestSession.isCreatedEventNotReceivedTillTimeout()) {
				System.out.println("Create event not received" + new Date());
				break;
			}
		}
	}
	
	private IPrompt prompt;
	
	public void setPrompt(IPrompt prompt){
		this.prompt=prompt;
	}
	public IPrompt getPrompt(){
		return prompt;
	}
}