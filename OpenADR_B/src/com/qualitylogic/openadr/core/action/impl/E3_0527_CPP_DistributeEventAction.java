package com.qualitylogic.openadr.core.action.impl;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E3_0527_CPP_DistributeEventAction extends
		BaseDistributeEventAction {

	private static final long serialVersionUID = 1L;
	protected boolean isEventCompleted;
	private OadrDistributeEventType oadrDistributeEvent = null;

	public boolean isPreConditionsMet(OadrRequestEventType oadrRequestEvent) {
		return true;
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
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.NEAR);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(0).getDuration()
					.setDuration("PT5M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().remove(2);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().remove(1);

			DurationPropType xEIRampUp = new DurationPropType();
			xEIRampUp.setDuration("PT4M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties()
					.setXEiRampUp(xEIRampUp);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT5M");

		}
		return oadrDistributeEvent;
	}

}