package com.qualitylogic.openadr.core.action;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.common.TestSession;

public class CreatedEventActionList {

	public static ArrayList<ICreatedEventAction> getCreatedEventActionList() {
		return TestSession.getCreatedEventActionList();
		
	}

	public static void addCreatedEventAction(
			ICreatedEventAction createdEventAction) {
		getCreatedEventActionList().add(createdEventAction);
	}

	public static ICreatedEventAction getNextCreatedEventAction() {
		ArrayList<ICreatedEventAction> createdEventActionList = getCreatedEventActionList();
		for (ICreatedEventAction eachCreatedEventAction : createdEventActionList) {
			if (!eachCreatedEventAction.isEventCompleted()) {
				eachCreatedEventAction.setEventCompleted(true);
				return eachCreatedEventAction;
			}
		}
		return null;
	}
}