package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCanceledOptAckActionList {

	public static void addResponseCanceledOptAckAction(
			IResponseCanceledOptAckAction responseCanceledOptAckAction) {
		TestSession.getResponseCanceledOptAckActionList().add(responseCanceledOptAckAction);
	}

	public static IResponseCanceledOptAckAction getNextResponseEventAction() {
		ArrayList<IResponseCanceledOptAckAction> responseEventActionList = TestSession
				.getResponseCanceledOptAckActionList();
		for (IResponseCanceledOptAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}