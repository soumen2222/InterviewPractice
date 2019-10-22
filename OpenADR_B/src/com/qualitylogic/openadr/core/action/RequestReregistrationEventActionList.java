package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;

public class RequestReregistrationEventActionList {

	public static synchronized ArrayList<IRequestReregistrationAction> getRequestReregistrationActionCompletedList() {
		ArrayList<IRequestReregistrationAction> requestReregistrationActionList = TestSession
				.getRequestReregistrationActionList();

		ArrayList<IRequestReregistrationAction> requestReregistrationActionCompletedList = new ArrayList<IRequestReregistrationAction>();
		for (IRequestReregistrationAction eachRequestReregistrationAction : requestReregistrationActionList) {
			if (eachRequestReregistrationAction.isEventCompleted()) {
				requestReregistrationActionCompletedList
						.add(eachRequestReregistrationAction);
			}
		}
		return requestReregistrationActionCompletedList;
	}

	public static synchronized ArrayList<IRequestReregistrationAction> getRequestReregistrationActionList() {
		return TestSession.getRequestReregistrationActionList();
	}

	public static synchronized void addRequestReregistrationAction(
			IRequestReregistrationAction requestReregistrationAction) {
		TestSession.getRequestReregistrationActionList().add(requestReregistrationAction);
	}

	public static synchronized IRequestReregistrationAction getPreviousCompletedRequestReregistrationEventAction(ServiceType serviceType) {
		ArrayList<IRequestReregistrationAction> requestReregistrationActionList = getRequestReregistrationActionList();
		IRequestReregistrationAction previousRequestReregistrationAction = null;
		for (IRequestReregistrationAction eachRequestReregistrationAction : requestReregistrationActionList) {
			if (eachRequestReregistrationAction.isEventCompleted()
					&& eachRequestReregistrationAction.isPreConditionsMet()) {
				previousRequestReregistrationAction = eachRequestReregistrationAction;
			} else {
				if (previousRequestReregistrationAction != null) {
					return previousRequestReregistrationAction;
				}
			}
		}

		return previousRequestReregistrationAction;
	}

	public static synchronized IRequestReregistrationAction getNextRequestReregistrationAction() {
		ArrayList<IRequestReregistrationAction> requestReregistrationActionList = getRequestReregistrationActionList();
		for (IRequestReregistrationAction eachRequestReregistrationAction : requestReregistrationActionList) {
			//if (!eachRequestReregistrationAction.isEventCompleted() &&!eachRequestReregistrationAction.isEventPickedUpFromOadrPoll()) {
			if (!eachRequestReregistrationAction.isEventCompleted()) {
				eachRequestReregistrationAction.setEventCompleted(true);
				return eachRequestReregistrationAction;
			}
		}
		return null;
	}
}