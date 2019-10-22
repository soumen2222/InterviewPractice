package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseRegisteredReportTypeAckActionList {

	public static void addResponseRegisteredReportAckAction(
			IResponseRegisteredReportTypeAckAction responseRegisteredReportAckAction) {
		
		TestSession.getResponseRegisteredReportTypeAckActionList().add(responseRegisteredReportAckAction);
	}

	public static IResponseRegisteredReportTypeAckAction getNextResponseRegisteredReportAckAction() {
		ArrayList<IResponseRegisteredReportTypeAckAction> responseRegisteredReportTypeAckActionList = TestSession
				.getResponseRegisteredReportTypeAckActionList();
		for (IResponseRegisteredReportTypeAckAction eachResponseEventAction : responseRegisteredReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}