package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseUpdatedReportTypeAckActionList {

	public static void addResponseUpdatedReportAckAction(
			IResponseUpdatedReportTypeAckAction responseUpdatedReportTypeAckAction) {
		
		TestSession.getResponseUpdatedReportTypeAckActionList().add(responseUpdatedReportTypeAckAction);
	}

	public static IResponseUpdatedReportTypeAckAction getNextResponseUpdatedReportAckAction() {
		ArrayList<IResponseUpdatedReportTypeAckAction> responseUpdatedReportTypeAckActionList = TestSession
				.getResponseUpdatedReportTypeAckActionList();
		for (IResponseUpdatedReportTypeAckAction eachResponseEventAction : responseUpdatedReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}