package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCreatedPartyRegistrationToQueryAckActionList {

	public static void addResponsePartyRegistrationAckAction(
			IResponseCreatedPartyRegistrationAckAction responseCreatedOptAckAction) {
		TestSession.getCreatedPartyRegistrationToQueryAckActionList().add(responseCreatedOptAckAction);
	}

	public static IResponseCreatedPartyRegistrationAckAction getNextResponseCreatedPartyRegistrationActionAction() {
		ArrayList<IResponseCreatedPartyRegistrationAckAction> responseEventActionList = TestSession
				.getCreatedPartyRegistrationToQueryAckActionList();
		for (IResponseCreatedPartyRegistrationAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}