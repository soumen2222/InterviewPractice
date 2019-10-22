package com.qualitylogic.openadr.core.base;

import java.util.Date;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.ven.CreatedEventTimeoutThread;

public abstract class BaseCreatedEventAction implements ICreatedEventAction {
	protected boolean isEventCompleted;
	protected OadrCreatedEventType oadrCreatedEvent = null;
	private ICreatedEventResult createdEventTimeoutAction;

	public boolean isCommonPreConditionsMet() {
		return true;
	}

	public void resetToInitialState() {
		isEventCompleted = false;
		oadrCreatedEvent = null;
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
}