package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseRegisterReportTypeAckActionList {

	public static void addResponseRegisterReportAckAction(
			IResponseRegisterReportTypeAckAction responseRegisterReportAckAction) {
		
		TestSession.getResponseRegisterReportTypeAckActionList().add(responseRegisterReportAckAction);
	}

	public static IResponseRegisterReportTypeAckAction getNextResponseRegisterReportAckAction() {
		ArrayList<IResponseRegisterReportTypeAckAction> responseRegisterReportTypeAckActionList = TestSession.getResponseRegisterReportTypeAckActionList();
		for (IResponseRegisterReportTypeAckAction eachResponseEventAction : responseRegisterReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}