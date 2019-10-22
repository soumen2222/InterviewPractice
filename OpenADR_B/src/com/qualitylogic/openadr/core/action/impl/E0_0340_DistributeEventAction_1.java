package com.qualitylogic.openadr.core.action.impl;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E0_0340_DistributeEventAction_1 extends BaseDistributeEventAction {


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
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_twoEvents_Default.xml");
			int firstIntervalStartTimeDelayMin = 5;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);

			EiEventType secondEvent = oadrDistributeEvent.getOadrEvent().get(1)
					.getEiEvent();
			XMLGregorianCalendar secondEventDateTime = (XMLGregorianCalendar) secondEvent
					.getEventDescriptor().getCreatedDateTime().clone();
			// Set back time 20 minutes
			Duration durationToAdd = OadrUtil.createDuration(-20, 0); 
			secondEventDateTime.add(durationToAdd);
			secondEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(secondEventDateTime);

			// Make the second event completed
			secondEvent.getEventDescriptor().setEventStatus(
					EventStatusEnumeratedType.COMPLETED);
		}
		return oadrDistributeEvent;

	}
}