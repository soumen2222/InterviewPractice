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

public class E1_0090_DistributeEventAction_2 extends BaseDistributeEventAction {

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
			distributeEvent2.getEiResponse().setRequestID("");
			distributeEvent2.setRequestID(newDistributeEventID);

			XMLGregorianCalendar currentStrtTime = (XMLGregorianCalendar) distributeEvent2
					.getOadrEvent().get(0).getEiEvent().getEiActivePeriod()
					.getProperties().getDtstart().getDateTime().clone();
			Duration durationToAdd = OadrUtil.createDuration(1, 0);
			currentStrtTime.add(durationToAdd);
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(currentStrtTime);
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(1);
			oadrDistributeEvent = distributeEvent2;

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