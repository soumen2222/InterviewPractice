package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCancelReportTypeAckActionList {

	public static void addCancelRegisterReportAckAction(
			IResponseCancelReportTypeAckAction responseCancelReportAckAction) {
		
		TestSession.getResponseCancelReportTypeAckActionList().add(responseCancelReportAckAction);
	}

	public static IResponseCancelReportTypeAckAction getNextCancelRegisterReportAckAction() {
		ArrayList<IResponseCancelReportTypeAckAction> responseCancelReportTypeAckActionList = TestSession.getResponseCancelReportTypeAckActionList();
		for (IResponseCancelReportTypeAckAction eachResponseEventAction : responseCancelReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}