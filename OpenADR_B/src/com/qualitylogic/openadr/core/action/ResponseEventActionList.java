package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseEventActionList {

	public static void addResponseEventAction(
			IResponseEventAction responseEventAction) {
		TestSession.getResponseEventActionList().add(responseEventAction);
	}

	public static IResponseEventAction getNextResponseEventAction() {
		ArrayList<IResponseEventAction> responseEventActionList = TestSession
				.getResponseEventActionList();
		for (IResponseEventAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}