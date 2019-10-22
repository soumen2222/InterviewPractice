package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCreatedPartyRegistrationAckActionList {

	public static void addResponsePartyRegistrationAckAction(
			IResponseCreatedPartyRegistrationAckAction responseCreatedOptAckAction) {
		TestSession.getCreatedPartyRegistrationAckActionList().add(responseCreatedOptAckAction);
	}

	public static IResponseCreatedPartyRegistrationAckAction getNextResponseCreatedPartyRegistrationActionAction() {
		ArrayList<IResponseCreatedPartyRegistrationAckAction> responseEventActionList = TestSession
				.getCreatedPartyRegistrationAckActionList();
		
		for (IResponseCreatedPartyRegistrationAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}