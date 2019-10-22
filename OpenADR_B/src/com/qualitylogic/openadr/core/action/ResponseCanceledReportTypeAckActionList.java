package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCanceledReportTypeAckActionList {

	public static void addResponseCanceledReportAckAction(
			IResponseCanceledReportTypeAckAction responseCanceledReportTypeAckAction) {
		
		TestSession.getResponseCanceledReportTypeAckActionList().add(responseCanceledReportTypeAckAction);
	}

	public static IResponseCanceledReportTypeAckAction getNextResponseCanceledReportAckAction() {
		ArrayList<IResponseCanceledReportTypeAckAction> responseCanceledReportTypeAckActionList = TestSession
				.getResponseCanceledReportTypeAckActionList();
		for (IResponseCanceledReportTypeAckAction eachResponseEventAction : responseCanceledReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}