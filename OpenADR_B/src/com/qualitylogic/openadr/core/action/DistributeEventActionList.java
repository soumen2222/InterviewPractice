package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;

public class DistributeEventActionList {

	public static synchronized ArrayList<IDistributeEventAction> getDistributeEventActionCompletedList() {
		ArrayList<IDistributeEventAction> distributeEventActionList = TestSession
				.getDistributeEventActionList();

		ArrayList<IDistributeEventAction> distributeEventActionCompletedList = new ArrayList<IDistributeEventAction>();
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionList) {
			if (eachDistributeEventAction.isEventCompleted()) {
				distributeEventActionCompletedList
						.add(eachDistributeEventAction);
			}
		}
		return distributeEventActionCompletedList;
	}

	public static synchronized ArrayList<IDistributeEventAction> getDistributeEventActionList() {
		return TestSession.getDistributeEventActionList();
	}

	public static synchronized void addDistributeEventAction(
			IDistributeEventAction distributeEventAction) {
		TestSession.getDistributeEventActionList().add(distributeEventAction);
	}

	public static synchronized IDistributeEventAction getPreviousCompletedDistributeEventAction(
			OadrRequestEventType oadrRequestEvent) {
		ArrayList<IDistributeEventAction> distributeEventActionList = getDistributeEventActionList();
		IDistributeEventAction previousDistributeEventAction = null;
		for (IDistributeEventAction eachDistributeaction : distributeEventActionList) {
			if (eachDistributeaction.isEventCompleted()
					&& eachDistributeaction
							.isCommonPreConditionsMet(oadrRequestEvent)) {
				previousDistributeEventAction = eachDistributeaction;
			} else {
				if (previousDistributeEventAction != null) {
					return previousDistributeEventAction;
				}
			}
		}

		return previousDistributeEventAction;
	}

	public static synchronized IDistributeEventAction getNextDistributeEventAction() {
		ArrayList<IDistributeEventAction> distributeEventActionList = getDistributeEventActionList();
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionList) {
			//if (!eachDistributeEventAction.isEventCompleted()&&!eachDistributeEventAction.isEventPickedUpFromOadrPoll()) {
			if (!eachDistributeEventAction.isEventCompleted()) {
				eachDistributeEventAction.setEventCompleted(true);
				return eachDistributeEventAction;
			}
		}
		return null;
	}
}