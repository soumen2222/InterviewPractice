package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class Empty_Pull_CPP_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;

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
			int firstIntervalStartTimeDelayMin = 0;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			oadrDistributeEvent.getOadrEvent().remove(0);

		}
		return oadrDistributeEvent;
	}

}