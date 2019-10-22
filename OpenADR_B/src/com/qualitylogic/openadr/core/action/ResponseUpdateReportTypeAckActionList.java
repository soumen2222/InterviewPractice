package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class ResponseUpdateReportTypeAckActionList {

	public static void addUpdateRegisterReportAckAction(
			IResponseUpdateReportTypeAckAction responseUpdateReportAckAction) {
		
		TestSession.getResponseUpdateReportTypeAckActionList().add(responseUpdateReportAckAction);
	}

	public static IResponseUpdateReportTypeAckAction getNextUpdateRegisterReportAckAction() {
		ArrayList<IResponseUpdateReportTypeAckAction> responseUpdateReportTypeAckActionList = TestSession.getResponseUpdateReportTypeAckActionList();
		for (IResponseUpdateReportTypeAckAction eachResponseEventAction : responseUpdateReportTypeAckActionList) {
			if (!eachResponseEventAction.isEventCompleted()) {
				eachResponseEventAction.setEventCompleted(true);
				return eachResponseEventAction;
			}
		}
		return null;
	}
}