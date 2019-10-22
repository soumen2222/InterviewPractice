package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E0_0100_DistributeEventAction_2 extends BaseDistributeEventAction {

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

			OadrDistributeEventType distributeEvent = TestSession
					.getDistributeEventActionList().get(0).getDistributeEvent();

			OadrDistributeEventType distributeEvent2 = Clone.clone(distributeEvent);
			String newDistributeEventID = OadrUtil
					.createoadrDistributeRequestID();
			distributeEvent2.setRequestID(newDistributeEventID);

			// Extend Duration
			oadrDistributeEvent = distributeEvent2;
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDuration()
					.setDuration("PT4M");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(2).getDuration()
					.setDuration("PT2M");
			
			
			
			//SignalPayloadType activeIntervalPayloadFloat = (SignalPayloadType)oadrDistributeEvent.getOadrEvent().get(0).getEiEvent().getEiEventSignals().getEiEventSignal().get(0).getIntervals().getInterval().get(2).getStreamPayloadBase().get(0).getValue();			
			//PayloadFloatType payloadFloat =(PayloadFloatType)activeIntervalPayloadFloat.getPayloadBase().getValue();
			
			
			//activeIntervalPayloadFloat.getPayloadFloat();

			SignalPayloadType signalPayloadFloat = (SignalPayloadType)oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
			.getEiEventSignals().getEiEventSignal().get(0)
			.getIntervals().getInterval().get(2).getStreamPayloadBase().get(0).getValue();
			
			//signalPayloadFloat.getPayloadFloat().setValue(1);
			((PayloadFloatType)signalPayloadFloat.getPayloadBase().getValue()).setValue(1);
			
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
			.getEiEventSignals().getEiEventSignal().get(0)
			.getCurrentValue().getPayloadFloat().setValue(1);
			
			/*
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getIntervals().getInterval().get(2).getSignalPayload().
					.getPayloadFloat().setValue(1);
			*/
			
			// Set eventStatus and CurrentValue
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(1);

			List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				XMLGregorianCalendar createdDateTime = (XMLGregorianCalendar) currentTime
						.clone();
				eachOadrEvent.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(createdDateTime);
			}

		}
		return oadrDistributeEvent;

	}
}