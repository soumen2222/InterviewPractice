package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class Default_Push_CPP_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;
	int firstIntervalStartTimeDelay = -999;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		return true;
	}

	public Default_Push_CPP_DistributeEventAction() {

	}

	public Default_Push_CPP_DistributeEventAction(
			int firstIntervalStartTimeDelay) {
		this.firstIntervalStartTimeDelay = firstIntervalStartTimeDelay;
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
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_Default.xml");
			int firstIntervalStartTimeDelayMin = 2;
			if (firstIntervalStartTimeDelay != -999) {
				firstIntervalStartTimeDelayMin = firstIntervalStartTimeDelay;
			}
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
		}
		
		return oadrDistributeEvent;
	}

}