package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E0_0190_DistributeEventAction_2 extends BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;

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

			// Get requestID from previous oadrDistributeEvent
			OadrDistributeEventType preqDistributeEvent = TestSession
					.getDistributeEventActionList().get(0).getDistributeEvent();
			String preqReqID = preqDistributeEvent.getRequestID();

			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_Default.xml");
			int firstIntervalStartTimeDelayMin = 20;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
			// Set requestID to the same as the previous oadrDistributeEvent
			oadrDistributeEvent.setRequestID(preqReqID);

		}
		return oadrDistributeEvent;

	}
}