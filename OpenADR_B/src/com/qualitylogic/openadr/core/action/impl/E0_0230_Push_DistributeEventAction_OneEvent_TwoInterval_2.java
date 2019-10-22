package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E0_0230_Push_DistributeEventAction_OneEvent_TwoInterval_2 extends
		BaseDistributeEventAction {

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

			OadrDistributeEventType oadrDistributeEventOriginal = TestSession
					.getDistributeEventActionList().get(0).getDistributeEvent();
			oadrDistributeEvent = Clone.clone(oadrDistributeEventOriginal);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);

			String distributeEventRequestID = OadrUtil
					.createoadrDistributeRequestID();
			oadrDistributeEvent.setRequestID(distributeEventRequestID);
			XMLGregorianCalendar createdDateTime = OadrUtil.getCurrentTime();
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(createdDateTime);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT5M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(0).getDuration()
					.setDuration("PT3M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(1).getDuration()
					.setDuration("PT2M");
			// Set eventStatus and CurrentValue
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(1);
		}
		return oadrDistributeEvent;
	}

}