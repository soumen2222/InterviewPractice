package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E1_0250_DistributeEventAction_1 extends BaseDistributeEventAction {

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
			oadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Pull_Dispatch_Default.xml");
			int firstIntervalStartTimeDelayMin = 2;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
			
			

			SignalPayloadType signalPayloadFloat = (SignalPayloadType)oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
			.getEiEventSignals().getEiEventSignal().get(0)
			.getIntervals().getInterval().get(0).getStreamPayloadBase().get(0).getValue();
			
			//signalPayloadFloat.getPayloadFloat().setValue(2);
			((PayloadFloatType)signalPayloadFloat.getPayloadBase().getValue()).setValue(2);			
			
			/*
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(0).getSignalPayload()
					.getPayloadFloat().setValue(2);
			 */

		}
		return oadrDistributeEvent;

	}
}