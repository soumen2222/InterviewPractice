package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCreatedReportTypeAckActionList {

	public static void addResponseCreatedReportAckAction(
			IResponseCreatedReportTypeAckAction responseCreatedReportTypeAckAction) {
		
		TestSession.getResponseCreatedReportTypeAckActionList().add(responseCreatedReportTypeAckAction);
	}

	public static IResponseCreatedReportTypeAckAction getNextResponseCreatedReportAckAction() {
		ArrayList<IResponseCreatedReportTypeAckAction> responseRegisteredReportTypeAckActionList = TestSession
				.getResponseCreatedReportTypeAckActionList();
		for (IResponseCreatedReportTypeAckAction eachResponseEventAction : responseRegisteredReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}