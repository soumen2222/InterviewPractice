package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.base.BaseDistributeEventAction;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class E1_0220_Pull_DistributeEventAction_OneEvent_TwoInterval_2 extends
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
			XMLGregorianCalendar createdDateTime = OadrUtil.getCurrentTime();

			XMLGregorianCalendar startDateTime = (XMLGregorianCalendar) createdDateTime
					.clone();

			Duration durationToAdd = OadrUtil.createDuration(1, 0);
			startDateTime.add(durationToAdd);

			String distributeEventRequestID = OadrUtil
					.createoadrDistributeRequestID();
			oadrDistributeEvent.setRequestID(distributeEventRequestID);
			oadrDistributeEvent.getEiResponse().setRequestID("");
			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setCreatedDateTime(createdDateTime);

			oadrDistributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(startDateTime);

			List<OadrEvent> oadrEventList = oadrDistributeEvent.getOadrEvent();
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				XMLGregorianCalendar createdTime = (XMLGregorianCalendar) currentTime
						.clone();
				eachOadrEvent.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(createdTime);
			}

		}
		return oadrDistributeEvent;
	}

}