package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseCreateReportTypeAckActionList {

	public static void addCreateRegisterReportAckAction(
			IResponseCreateReportTypeAckAction responseCreateReportAckAction) {
		
		TestSession.getResponseCreateReportTypeAckActionList().add(responseCreateReportAckAction);
	}

	public static IResponseCreateReportTypeAckAction getNextCreateRegisterReportAckAction() {
		ArrayList<IResponseCreateReportTypeAckAction> responseCreateReportTypeAckActionList = TestSession.getResponseCreateReportTypeAckActionList();
		for (IResponseCreateReportTypeAckAction eachResponseEventAction : responseCreateReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}