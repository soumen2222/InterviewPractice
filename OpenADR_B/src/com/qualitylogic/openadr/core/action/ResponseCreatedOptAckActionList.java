package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCreatedOptAckActionList {

	public static void addResponseCreatedOptAckAction(
			IResponseCreatedOptAckAction responseCreatedOptAckAction) {
		TestSession.getResponseCreatedOptAckActionList().add(responseCreatedOptAckAction);
	}

	public static IResponseCreatedOptAckAction getNextResponseEventAction() {
		ArrayList<IResponseCreatedOptAckAction> responseEventActionList = TestSession
				.getResponseCreatedOptAckActionList();
		for (IResponseCreatedOptAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}