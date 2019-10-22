package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCanceledPartyRegistrationAckActionList {

	public static void addResponseCanceledPartyRegistrationAckAction(
			IResponseCanceledPartyRegistrationAckAction responseCreatedOptAckAction) {
		TestSession.getResponseCanceledPartyRegistrationAckActionList().add(responseCreatedOptAckAction);
	}

	public static IResponseCanceledPartyRegistrationAckAction getNextResponseCanceledPartyRegistrationActionAction() {
		ArrayList<IResponseCanceledPartyRegistrationAckAction> responseEventActionList = TestSession
				.getResponseCanceledPartyRegistrationAckActionList();
		for (IResponseCanceledPartyRegistrationAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}