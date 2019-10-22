package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCancelPartyRegistrationAckActionList {

	public static void addResponseCancelPartyRegistrationAckAction(
			IResponseCancelPartyRegistrationAckAction responseCancelAckAction) {
		TestSession.getResponseCancelPartyRegistrationAckActionList().add(responseCancelAckAction);
	
	}

	public static IResponseCancelPartyRegistrationAckAction getNextResponseCancelPartyRegistrationActionAction() {
		ArrayList<IResponseCancelPartyRegistrationAckAction> responseEventActionList = TestSession
				.getResponseCancelPartyRegistrationAckActionList();
		for (IResponseCancelPartyRegistrationAckAction eachResponseEventAction : responseEventActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}