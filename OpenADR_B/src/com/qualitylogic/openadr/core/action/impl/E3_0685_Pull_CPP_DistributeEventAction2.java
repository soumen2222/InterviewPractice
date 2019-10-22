package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E3_0685_Pull_CPP_DistributeEventAction2 extends
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
			int firstIntervalStartTimeDelayMin = 1;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().remove(1);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().remove(1);
			// distributeEvent.getOadrEvent().remove(1);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT1M");

		}
		return oadrDistributeEvent;
	}

}