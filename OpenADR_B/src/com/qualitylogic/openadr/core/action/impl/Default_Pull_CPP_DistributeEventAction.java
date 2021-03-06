package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class Default_Pull_CPP_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	int firstIntervalStartTimeDelay = -999;

	public Default_Pull_CPP_DistributeEventAction(
			int firstIntervalStartTimeDelayMin) {
		this.firstIntervalStartTimeDelay = firstIntervalStartTimeDelayMin;
	}

	public Default_Pull_CPP_DistributeEventAction() {

	}

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {

		boolean isCommonPreConditionsMet = isCommonPreConditionsMet(oadrRequestEvent);

		return isCommonPreConditionsMet;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrDistributeEventType getDistributeEvent() {

		if (oadrDistributeEvent == null) {
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_CPP_Default.xml");
			int firstIntervalStartTimeDelayMin = 2;
			if (this.firstIntervalStartTimeDelay != -999) {
				firstIntervalStartTimeDelayMin = firstIntervalStartTimeDelay;
			}
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
		}
		return oadrDistributeEvent;
	}

}